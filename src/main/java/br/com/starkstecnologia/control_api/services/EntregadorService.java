package br.com.starkstecnologia.control_api.services;


import br.com.starkstecnologia.control_api.dto.DadosCaixaEntregadorDTO;
import br.com.starkstecnologia.control_api.dto.DadosEntregadorDTO;
import br.com.starkstecnologia.control_api.dto.DadosUsuarioLogadoDTO;
import br.com.starkstecnologia.control_api.entity.Entregador;
import br.com.starkstecnologia.control_api.exception.EntityNotFoundException;
import br.com.starkstecnologia.control_api.exception.SQLIntegrityConstraintViolationException;
import br.com.starkstecnologia.control_api.repository.EntregadorRepository;

import br.com.starkstecnologia.control_api.repository.MatrizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntregadorService {

    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MatrizRepository matrizRepository;

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
        entregador.setMatriz(matrizRepository.getReferenceById(caixaDTO.getIdMatriz()));
        entregadorRepository.save(entregador);
    }

    public ResponseEntity<?> atualizar(Long idEntregador, DadosCaixaEntregadorDTO caixaDTO){
        Entregador entregador = entregadorRepository.findById(idEntregador).orElse(null);
        if(entregador != null){
            entregador.setNome(caixaDTO.getNome());
            entregador.setSenha(passwordEncoder.encode(caixaDTO.getSenha()));
            entregador.setUsuario(caixaDTO.getUsuario());
            entregador.setAtivo(entregador.isAtivo());
            entregador.setTurno(caixaDTO.getTurno());
            entregador.setTelefone(caixaDTO.getTelefone());
            entregador.setMatriz(matrizRepository.getReferenceById(caixaDTO.getIdMatriz()));
            entregadorRepository.save(entregador);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> listarTodos() {
        List<Entregador> listEntregador = entregadorRepository.findAll();
        List<DadosEntregadorDTO> dadosEntregadorDTOS = listEntregador.stream().map(EntregadorService::getDadosEntregadorDTO).toList();
        return ResponseEntity.ok(dadosEntregadorDTOS);
    }

    public ResponseEntity<?> buscarPorId(Long id) {
        Entregador entregador = entregadorRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Entregador não encontrado"));
        DadosEntregadorDTO dadosEntregadorDTO = getDadosEntregadorDTO(entregador);
        return ResponseEntity.ok(dadosEntregadorDTO);
    }

    private static DadosEntregadorDTO getDadosEntregadorDTO(Entregador entregador) {
        DadosEntregadorDTO dadosEntregadorDTO = new DadosEntregadorDTO();
        dadosEntregadorDTO.setIdEntregador(entregador.getIdEntregador());
        dadosEntregadorDTO.setAtivo(entregador.isAtivo());
        dadosEntregadorDTO.setNome(entregador.getNome());
        dadosEntregadorDTO.setUsuario(entregador.getUsuario());
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
        dados.setQuantidadeEntregas(getQuantidadeEntregas(userId));
        return dados;
        }
        throw new EntityNotFoundException("Erro ao realizar login, verifique os dados");
    }

    public int getQuantidadeEntregas(String userId) {
        return entregadorRepository.quantidadeEntregasFinalizadasMes(userId);
    }

}
