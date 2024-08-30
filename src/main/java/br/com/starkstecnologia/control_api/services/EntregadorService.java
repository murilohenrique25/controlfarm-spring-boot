package br.com.starkstecnologia.control_api.services;


import br.com.starkstecnologia.control_api.dto.DadosCaixaEntregadorDTO;
import br.com.starkstecnologia.control_api.dto.DadosUsuarioLogadoDTO;
import br.com.starkstecnologia.control_api.entity.Entregador;
import br.com.starkstecnologia.control_api.exception.EntityNotFoundException;
import br.com.starkstecnologia.control_api.exception.ServiceException;
import br.com.starkstecnologia.control_api.repository.EntregaRepository;
import br.com.starkstecnologia.control_api.repository.EntregadorRepository;

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
    EntregaRepository entregaRepository;

    public void salvar(DadosCaixaEntregadorDTO caixaDTO) throws EntityNotFoundException {
        if(entregadorRepository.verificaUsuarioJaExistente(caixaDTO.getUsuario())){
            throw new EntityNotFoundException("Já existe um Entregador com usuário ");
        }
        Entregador entregador = new Entregador();
        entregador.setNome(caixaDTO.getNome());
        entregador.setSenha(passwordEncoder.encode(caixaDTO.getSenha()));
        entregador.setUsuario(caixaDTO.getUsuario());
        entregador.setAtivo(true);
        entregadorRepository.save(entregador);
    }

    public ResponseEntity<?> atualizar(Long idCaixa, DadosCaixaEntregadorDTO caixaDTO){
        Entregador entregador = entregadorRepository.findById(idCaixa).orElse(null);
        if(entregador != null){
            entregador.setNome(caixaDTO.getNome());
            entregador.setSenha(caixaDTO.getSenha());
            entregador.setUsuario(caixaDTO.getUsuario());
            entregador.setAtivo(entregador.isAtivo());
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> listarTodos() {
        List<Entregador> listEntregador = entregadorRepository.findAll();
        return ResponseEntity.ok(listEntregador);
    }


    public ResponseEntity<?> remover(Long idEntregador){
        if(entregaRepository.existeEntregaVinculadaEntregador(idEntregador)) {
            throw new ServiceException("Impossível excluir entregador, já existe entrega vinculada");
        }else{
            Entregador entregador = entregadorRepository.findById(idEntregador).orElse(null);

            if (entregador != null) {
                entregadorRepository.delete(entregador);
                return ResponseEntity.status(200).build();
            }
            return ResponseEntity.noContent().build();
        }
    }

    public DadosUsuarioLogadoDTO logarApp(String userId, String password) throws EntityNotFoundException {
        Entregador entregadorLogado = entregadorRepository.logarApp(userId);
        if(entregadorLogado == null){
            throw new EntityNotFoundException("Erro ao realizar login, verifique os dados");
        }

        if(passwordEncoder.matches(password, entregadorLogado.getSenha())) {
        
        DadosUsuarioLogadoDTO dados = new DadosUsuarioLogadoDTO();
        dados.setUsuario(userId);
        dados.setQuantidadeEntregas(entregadorRepository.quantidadeEntregasFinalizadasMes(userId));
        return dados;
        }
        throw new EntityNotFoundException("Erro ao realizar login, verifique os dados");
    }
}
