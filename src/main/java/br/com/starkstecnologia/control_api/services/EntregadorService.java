package br.com.starkstecnologia.control_api.services;


import br.com.starkstecnologia.control_api.dto.*;
import br.com.starkstecnologia.control_api.entity.Entrega;
import br.com.starkstecnologia.control_api.entity.Entregador;
import br.com.starkstecnologia.control_api.exception.EntityNotFoundException;
import br.com.starkstecnologia.control_api.exception.SQLIntegrityConstraintViolationException;
import br.com.starkstecnologia.control_api.repository.EntregaRepository;
import br.com.starkstecnologia.control_api.repository.EntregadorRepository;

import br.com.starkstecnologia.control_api.repository.MatrizRepository;
import br.com.starkstecnologia.control_api.utils.StringUtils;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EntregadorService {

    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MatrizRepository matrizRepository;

    @Autowired
    EntregaRepository entregaRepository;

    public void salvar(DadosCaixaEntregadorDTO caixaDTO) throws EntityNotFoundException, SQLIntegrityConstraintViolationException {
        if(entregadorRepository.verificaUsuarioJaExistente(caixaDTO.getUsuario())){
            throw new EntityNotFoundException("Já existe um Entregador com usuário ");
        }
        Entregador entregador = new Entregador();
        entregador.setNome(caixaDTO.getNome());
        entregador.setSenha(passwordEncoder.encode(caixaDTO.getSenha()));
        entregador.setUsuario(caixaDTO.getUsuario());
        entregador.setAtivo(true);
        entregador.setTurno(caixaDTO.getTurno());
        entregador.setTelefone(caixaDTO.getTelefone());
        entregador.setCpf(caixaDTO.getCpf());
        entregador.setMatriz(matrizRepository.getReferenceById(caixaDTO.getIdMatriz()));
        entregadorRepository.save(entregador);
    }

    public ResponseEntity<?> atualizar(Long idEntregador, DadosCaixaEntregadorDTO caixaDTO){
        Entregador entregador = entregadorRepository.findById(idEntregador).orElse(null);
        if(entregador != null){
            entregador.setSenha(passwordEncoder.encode(caixaDTO.getSenha()));
            entregador.setAtivo(caixaDTO.isAtivo());
            entregador.setTurno(caixaDTO.getTurno());
            entregador.setTelefone(caixaDTO.getTelefone());
            entregador.setCpf(caixaDTO.getCpf());
            if(caixaDTO.getIdMatriz() != null) entregador.setMatriz(matrizRepository.getReferenceById(caixaDTO.getIdMatriz()));
            entregadorRepository.save(entregador);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> listarTodos() {
        List<Entregador> listEntregador = entregadorRepository.findAll();
        List<DadosRetornoEntregadorDTO> dadosEntregadorDTOS = listEntregador.stream().map(EntregadorService::getDadosEntregadorDTO).toList();
        return ResponseEntity.ok(dadosEntregadorDTOS);
    }

    public ResponseEntity<?> buscarPorId(Long id) {
        Entregador entregador = entregadorRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Entregador não encontrado"));
        DadosRetornoEntregadorDTO dadosEntregadorDTO = getDadosEntregadorDTO(entregador);
        return ResponseEntity.ok(dadosEntregadorDTO);
    }

    private static DadosRetornoEntregadorDTO getDadosEntregadorDTO(Entregador entregador) {
        DadosRetornoEntregadorDTO dadosEntregadorDTO = new DadosRetornoEntregadorDTO();
        dadosEntregadorDTO.setIdEntregador(entregador.getIdEntregador());
        dadosEntregadorDTO.setAtivo(entregador.isAtivo());
        dadosEntregadorDTO.setNome(entregador.getNome());
        dadosEntregadorDTO.setUsuario(entregador.getUsuario());
        dadosEntregadorDTO.setIdMatriz(entregador.getMatriz().getIdMatriz());
        dadosEntregadorDTO.setNomeMatriz(entregador.getMatriz().getNome());
        dadosEntregadorDTO.setTurno(entregador.getTurno());
        dadosEntregadorDTO.setTelefone(entregador.getTelefone());
        dadosEntregadorDTO.setCpf(entregador.getCpf());
        return dadosEntregadorDTO;
    }


    public ResponseEntity<?> desativarEntregador(Long idEntregador){
            Entregador entregador = entregadorRepository.findById(idEntregador).orElse(null);
            if (entregador != null) {
                entregador.setAtivo(false);
                entregadorRepository.save(entregador);
                return ResponseEntity.status(200).build();
            }
            return ResponseEntity.noContent().build();
    }

    public DadosUsuarioLogadoDTO logarApp(String userId, String password) throws EntityNotFoundException {
        Entregador entregadorLogado = entregadorRepository.logarApp(userId);
        if(entregadorLogado == null){
            throw new EntityNotFoundException("Erro ao realizar login, verifique os dados");
        }

        if(passwordEncoder.matches(password, entregadorLogado.getSenha())) {
        
        DadosUsuarioLogadoDTO dados = new DadosUsuarioLogadoDTO();
        dados.setUsuario(userId);
        dados.setDadosEntregadorApp(getQuantidadeEntregas(userId));
        return dados;
        }
        throw new EntityNotFoundException("Erro ao realizar login, verifique os dados");
    }

    public DadosEntregadorApp getQuantidadeEntregas(String userId) {
        LocalDateTime primeiroDia = StringUtils.retornaDataInicioAtual();
        LocalDateTime ultimoDia = StringUtils.retornaDataFinalAtual();

        Tuple retorno = entregaRepository.buscarDadosEntregasPorEntregadorApp(primeiroDia, ultimoDia, userId);

        DadosEntregadorApp dadosEntregadorApp = new DadosEntregadorApp();
        dadosEntregadorApp.setQuantidadeEntrega(retorno.get("quantidadeEntregas", Long.class));
        dadosEntregadorApp.setMediaTotal(StringUtils.convertSecondsToHHMMSS(retorno.get("mediaTempo", Double.class)));
        return dadosEntregadorApp;
    }

    public List<DadosInfoEntregaAppDTO> buscarDadosEntregasPorNaoFinalizadasNoDiaApp(){
        LocalDateTime primeiroDia = StringUtils.retornaDataInicioAtual();
        LocalDateTime ultimoDia = StringUtils.retornaDataFinalAtual();

        List<Tuple> retorno = entregaRepository.buscarDadosEntregasPorNaoFinalizadasNoDiaApp(primeiroDia, ultimoDia);
        List<DadosInfoEntregaAppDTO> dados = new ArrayList<>();
        for(Tuple tuple : retorno){
            DadosInfoEntregaAppDTO dado = new DadosInfoEntregaAppDTO();

            dado.setCupomOrcamento(tuple.get(0, String.class));
            dado.setStatusEntrega(tuple.get(1, String.class));
            dado.setDataCadastroEntrega(StringUtils.converterFormatoBrasileiro(tuple.get(2, LocalDateTime.class)));
            dados.add(dado);
        }

        return dados;

    }


}
