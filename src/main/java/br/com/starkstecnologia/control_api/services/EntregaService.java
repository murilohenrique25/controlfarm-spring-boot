package br.com.starkstecnologia.control_api.services;


import br.com.starkstecnologia.control_api.dto.DadosEntregaDTO;
import br.com.starkstecnologia.control_api.entity.Entrega;
import br.com.starkstecnologia.control_api.entity.Entregador;
import br.com.starkstecnologia.control_api.entity.Usuario;
import br.com.starkstecnologia.control_api.exception.EntityNotFoundException;
import br.com.starkstecnologia.control_api.exception.ServiceException;
import br.com.starkstecnologia.control_api.repository.EntregaRepository;
import br.com.starkstecnologia.control_api.repository.EntregadorRepository;
import br.com.starkstecnologia.control_api.repository.UsuarioRepository;
import br.com.starkstecnologia.control_api.types.StatusEntregaEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EntregaService {

    @Autowired
    EntregaRepository entregaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    EntregadorRepository entregadorRepository;

   
    public void salvarEntrega(DadosEntregaDTO entregaDTO){
        Entrega entrega;
        if(entregaDTO.getIdEntrega() == null){
            entrega = new Entrega();
        } else {
            entrega = entregaRepository.findById(entregaDTO.getIdEntrega()).get();
        }
        Usuario caixa = usuarioRepository.findById(entregaDTO.getIdUser()).orElseThrow(()-> new EntityNotFoundException("Usuário não encontrado"));
        dadosEntrega(entregaDTO, entrega, caixa);
        entrega.setStatusEntrega(StatusEntregaEnum.ABERTA.getDescricao());
        entregaRepository.save(entrega);
    }

    private static void dadosEntrega(DadosEntregaDTO entregaDTO, Entrega entrega, Usuario caixa) {
        entrega.setTipoEntrega(entregaDTO.getTipoEntrega());
        entrega.setDataCadastroEntrega(LocalDateTime.now());
        entrega.setCupomOrcamento(entregaDTO.getCupomOrcamento());
        entrega.setFormaPagamento(entregaDTO.getFormaPagamento());
        entrega.setValorTotal(entregaDTO.getValorTotal());
        entrega.setTroco(entregaDTO.getTroco());
        entrega.setValorReceber(entregaDTO.getValorReceber());
        entrega.setObservacao(entregaDTO.getObservacao());
        entrega.setUser(caixa);
    }

    public void alterarEntrega(DadosEntregaDTO entregaDTO){
      Entrega entrega = entregaRepository.findById(entregaDTO.getIdEntrega()).get();
        Usuario caixa = usuarioRepository.findById(entregaDTO.getIdUser()).orElseThrow(()-> new EntityNotFoundException("Usuário não encontrado"));
        dadosEntrega(entregaDTO, entrega, caixa);
        entregaRepository.flush();
    }

    public void selecionarAtribuirAlterarEntrega(List<DadosEntregaDTO> entregasDTO, String userId){
        Long idEntregador = entregadorRepository.findEntregadorByUsuario(userId);
        List<Entrega> entregas = entregaRepository.buscarEntregasPorId(entregasDTO.stream().map(DadosEntregaDTO::getIdEntrega).collect(Collectors.toList()));
        LocalDateTime dataAgora = LocalDateTime.now();
        Entregador entregador = entregadorRepository.findById(idEntregador).get();
        for(Entrega entrega : entregas){
            entrega.setDataSelecaoEntrega(dataAgora);
            entrega.setEntregador(entregador);
            entrega.setStatusEntrega(StatusEntregaEnum.EM_ROTA.getDescricao());
        }
    }

    public void assinarEntregaCaixa(List<DadosEntregaDTO> entregasDTO, Long idCaixa){
        List<Entrega> entregas = entregaRepository.buscarEntregasPorId(entregasDTO.stream().map(DadosEntregaDTO::getIdEntrega).collect(Collectors.toList()));
        LocalDateTime dataAgora = LocalDateTime.now();
        Usuario caixa = usuarioRepository.findById(idCaixa).orElseThrow(()-> new EntityNotFoundException("Usuário não encontrado"));
        for(Entrega entrega : entregas){
            entrega.setDataAssinaturaEntrega(dataAgora);
            entrega.setUser(caixa);
            entrega.setStatusEntrega(StatusEntregaEnum.FINALIZADA_ASSINADA.getDescricao());
        }
    }

    public void finalizarEntrega(DadosEntregaDTO entregaDTO, String latitude, String longitude) {
        Entrega entrega = entregaRepository.findById(entregaDTO.getIdEntrega()).get();
        LocalDateTime dataAgora = LocalDateTime.now();
        entrega.setDataFinalizacaoEntrega(dataAgora);
        entrega.setLatitude(latitude);
        entrega.setLongitude(longitude);
        entrega.setStatusEntrega(StatusEntregaEnum.FINALIZADA.getDescricao());
    }

    public List<DadosEntregaDTO> entregasDisponiveis(){
    	
        List<Entrega> entregas =entregaRepository.buscarEntregasDisponiveis();
        return convertDadosEntregaEntregasDisponiveisDTO(entregas);
    }


    public List<DadosEntregaDTO> entregasByIdEntregador(String userId){
        Long idEntregador = entregadorRepository.findEntregadorByUsuario(userId);
       List<Entrega> entregas = entregaRepository.buscarEntregasByIdEntregador(idEntregador);
       return convertEntregasEntregador(entregas);
    }

    public static List<DadosEntregaDTO> convertDadosEntregaEntregasDisponiveisDTO(List<Entrega> result) {

        List<DadosEntregaDTO> entregaDTOS = new ArrayList<>();

        for(Entrega entrega : result){
            DadosEntregaDTO dados = new DadosEntregaDTO();

            dados.setIdEntrega(entrega.getIdEntrega());
            dados.setTipoEntrega(entrega.getTipoEntrega());
            dados.setCupomOrcamento(entrega.getCupomOrcamento());
            dados.setFormaPagamento(entrega.getFormaPagamento());
            dados.setIdUser(entrega.getUser().getId());
            dados.setTroco(entrega.getTroco());
            dados.setValorReceber(entrega.getValorReceber());
            dados.setValorTotal(entrega.getValorTotal());
            dados.setDataCadastroEntrega(entrega.getDataCadastroEntrega());
            entregaDTOS.add(dados);
        }
        return entregaDTOS;
    }

    public static List<DadosEntregaDTO> convertEntregasEntregador(List<Entrega> resultList) {
        List<DadosEntregaDTO> entregaDTOS = new ArrayList<>();

        for(Entrega entrega : resultList){
            DadosEntregaDTO dados = new DadosEntregaDTO();

            dados.setIdEntrega(entrega.getIdEntrega());
            dados.setIdEntregador(entrega.getEntregador().getIdEntregador());
            dados.setTipoEntrega(entrega.getTipoEntrega());
            dados.setCupomOrcamento(entrega.getCupomOrcamento());
            dados.setFormaPagamento(entrega.getFormaPagamento());
            dados.setIdUser(entrega.getUser().getId());
            dados.setTroco(entrega.getTroco());
            dados.setValorReceber(entrega.getValorReceber());
            dados.setValorTotal(entrega.getValorTotal());
            dados.setDataSelecaoEntrega(entrega.getDataSelecaoEntrega());
            dados.setDataCadastroEntrega(entrega.getDataCadastroEntrega());
            entregaDTOS.add(dados);
        }
        return entregaDTOS;
    }

    public List<DadosEntregaDTO> buscarTodasEntregas() {

        List<Entrega> entregas = entregaRepository.findAll();
        return convertTodasEntregas(entregas);
    }

    private List<DadosEntregaDTO> convertTodasEntregas(List<Entrega> entregas) {
        List<DadosEntregaDTO> entregaDTOS = new ArrayList<>();

        for(Entrega entrega : entregas){
            DadosEntregaDTO dados = new DadosEntregaDTO();

            dados.setIdEntrega(entrega.getIdEntrega());
            dados.setIdEntregador(entrega.getEntregador().getIdEntregador());
            dados.setTipoEntrega(entrega.getTipoEntrega());
            dados.setCupomOrcamento(entrega.getCupomOrcamento());
            dados.setFormaPagamento(entrega.getFormaPagamento());
            dados.setIdUser(entrega.getUser().getId());
            dados.setTroco(entrega.getTroco());
            dados.setValorReceber(entrega.getValorReceber());
            dados.setValorTotal(entrega.getValorTotal());
            dados.setDataSelecaoEntrega(entrega.getDataSelecaoEntrega());
            dados.setDataCadastroEntrega(entrega.getDataCadastroEntrega());
            dados.setLatitude(entrega.getLatitude());
            dados.setLongitude(entrega.getLongitude());
            dados.setObservacao(entrega.getObservacao());
            dados.setDataFinalizacaoEntrega(entrega.getDataFinalizacaoEntrega());
            dados.setDataAssinaturaEntrega(entrega.getDataAssinaturaEntrega());
            entregaDTOS.add(dados);
        }
        return entregaDTOS;
    }

    public void finalizarEntregaManual(List<DadosEntregaDTO> entregaDTOS) {
       for(DadosEntregaDTO dadosEntregaDTO : entregaDTOS){
           Entrega entrega = entregaRepository.findById(dadosEntregaDTO.getIdEntrega()).get();
           LocalDateTime dataAgora = LocalDateTime.now();
           entrega.setDataFinalizacaoEntrega(dataAgora);
           entrega.setStatusEntrega(StatusEntregaEnum.FINALIZADA_MANUAL_ASSINADA.getDescricao());
       }
    }

    public void excluirEntrega(Long idEntrega) {
        Entrega entrega = entregaRepository.findById(idEntrega).orElseThrow(()-> new ServiceException("Entrega não encontrada"));
        if(StatusEntregaEnum.FINALIZADA.getDescricao().equals(entrega.getStatusEntrega()) ||
            StatusEntregaEnum.FINALIZADA_ASSINADA.getDescricao().equals(entrega.getStatusEntrega())
        || StatusEntregaEnum.FINALIZADA_MANUAL_ASSINADA.getDescricao().equals(entrega.getStatusEntrega())){
            throw new ServiceException("Entrega já assinada, não é permitido exclusão");
        }
        entregaRepository.delete(entrega);
    }
}
