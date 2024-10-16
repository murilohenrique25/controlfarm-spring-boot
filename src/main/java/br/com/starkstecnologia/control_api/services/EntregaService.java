package br.com.starkstecnologia.control_api.services;


import br.com.starkstecnologia.control_api.dto.DadosAtribuirFinalizarEntregaDTO;
import br.com.starkstecnologia.control_api.dto.DadosEntregaAvulsaDTO;
import br.com.starkstecnologia.control_api.dto.DadosEntregaDTO;
import br.com.starkstecnologia.control_api.dto.DadosEntregaFinalizacaoDTO;
import br.com.starkstecnologia.control_api.entity.*;
import br.com.starkstecnologia.control_api.exception.EntityNotFoundException;
import br.com.starkstecnologia.control_api.exception.ServiceException;
import br.com.starkstecnologia.control_api.repository.*;
import br.com.starkstecnologia.control_api.types.StatusEntregaEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    EntregaAvulsaRepository entregaAvulsaRepository;

    @Autowired
    MatrizRepository matrizRepository;


    public Long salvarEntrega(DadosEntregaDTO entregaDTO) {
        Entrega entrega;
        if (entregaDTO.getIdEntrega() == null) {
            entrega = new Entrega();
        } else {
            entrega = entregaRepository.findById(entregaDTO.getIdEntrega()).orElseThrow(()-> new EntityNotFoundException("Entrega não encontrada"));
        }
        Usuario caixa = usuarioRepository.findById(entregaDTO.getIdUser()).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        dadosEntrega(entregaDTO, entrega, caixa);
        entrega.setStatusEntrega(StatusEntregaEnum.ABERTA.getDescricao());
        Entrega entregaSave = entregaRepository.save(entrega);
        return entregaSave.getIdEntrega();
    }

    private void dadosEntrega(DadosEntregaDTO entregaDTO, Entrega entrega, Usuario caixa) {
        entrega.setDataCadastroEntrega(LocalDateTime.now());
        entrega.setCupomOrcamento(entregaDTO.getCupomOrcamento());
        entrega.setFormaPagamento(entregaDTO.getFormaPagamento());
        entrega.setValorTotal(entregaDTO.getValorTotal());
        entrega.setTroco(entregaDTO.getTroco());
        entrega.setValorReceber(entregaDTO.getValorReceber());
        entrega.setObservacao(entregaDTO.getObservacao());
        entrega.setMatriz(matrizRepository.getReferenceById(entregaDTO.getIdMatriz()));
        entrega.setUser(caixa);
    }

    public void alterarEntrega(DadosEntregaDTO entregaDTO) {
        Entrega entrega = entregaRepository.findById(entregaDTO.getIdEntrega()).orElseThrow(() -> new EntityNotFoundException("Entrega não encontrada"));
        Entregador entregador = entregadorRepository.findById(entregaDTO.getIdEntregador()).orElseThrow(() -> new EntityNotFoundException("Entregador não encontrado"));
        dadosEntregaAlterar(entregaDTO, entrega);
        entrega.setEntregador(entregador);
        entregaRepository.flush();
    }

    private static void dadosEntregaAlterar(DadosEntregaDTO entregaDTO, Entrega entrega) {
        entrega.setDataCadastroEntrega(LocalDateTime.now());
        entrega.setCupomOrcamento(entregaDTO.getCupomOrcamento());
        entrega.setFormaPagamento(entregaDTO.getFormaPagamento());
        entrega.setValorTotal(entregaDTO.getValorTotal());
        entrega.setTroco(entregaDTO.getTroco());
        entrega.setValorReceber(entregaDTO.getValorReceber());
        entrega.setObservacao(entregaDTO.getObservacao());
    }

    public void selecionarAtribuirAlterarEntrega(List<DadosEntregaDTO> entregasDTO, String userId) {
        Entregador entregador = entregadorRepository.findEntregadorByUsuario(userId);
        List<Entrega> entregas = entregaRepository.buscarEntregasPorId(entregasDTO.stream().map(DadosEntregaDTO::getIdEntrega).collect(Collectors.toList()));
        LocalDateTime dataAgora = LocalDateTime.now();
        for (Entrega entrega : entregas) {
            entrega.setDataSelecaoEntrega(dataAgora);
            entrega.setEntregador(entregador);
            entrega.setStatusEntrega(StatusEntregaEnum.EM_ROTA.getDescricao());
        }
    }

    public void selecionarAtribuirAlterarEntrega(DadosAtribuirFinalizarEntregaDTO entregaDTO, Long idEntrega) {
        Entregador entregador = entregadorRepository.findById(entregaDTO.getId()).orElseThrow(()-> new ServiceException("Entregador não encontrado"));
        Entrega entrega = entregaRepository.findById(idEntrega).orElseThrow(()-> new ServiceException("Entrega não encontrada"));
        LocalDateTime dataAgora = LocalDateTime.now();
            entrega.setDataSelecaoEntrega(dataAgora);
            entrega.setEntregador(entregador);
            entrega.setStatusEntrega(StatusEntregaEnum.EM_ROTA.getDescricao());
    }

    public void assinarEntregaCaixa(DadosAtribuirFinalizarEntregaDTO entregaDTO, Long idEntrega) {
        Entrega entrega = entregaRepository.findById(idEntrega).orElseThrow(()-> new ServiceException("Entrega não encontrada"));
        LocalDateTime dataAgora = LocalDateTime.now();
        Usuario caixa = usuarioRepository.findById(entregaDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        entrega.setDataAssinaturaEntrega(dataAgora);
        entrega.setUserAssinatura(caixa);
        entrega.setStatusEntrega(StatusEntregaEnum.FINALIZADA_ASSINADA.getDescricao());

    }

    public void finalizarEntrega(DadosEntregaFinalizacaoDTO entregaDTO) {
        Entrega entrega = entregaRepository.findById(entregaDTO.getIdEntrega()).orElseThrow(()-> new EntityNotFoundException("Entrega não encontrada"));
        LocalDateTime dataAgora = LocalDateTime.now();
        entrega.setDataFinalizacaoEntrega(dataAgora);
        entrega.setLatitude(entregaDTO.getLatitude());
        entrega.setLongitude(entregaDTO.getLongitude());
        entrega.setObservacao(entregaDTO.getObservacao());
        if(entregaDTO.getObservacao().startsWith("Não Entregue")){
         entrega.setStatusEntrega(StatusEntregaEnum.CANCELADA.getDescricao());
        }else {
            entrega.setStatusEntrega(StatusEntregaEnum.FINALIZADA.getDescricao());
        }
    }

    public List<DadosEntregaDTO> entregasDisponiveis() {

        List<Entrega> entregas = entregaRepository.buscarEntregasDisponiveis();
        return convertDadosEntregaEntregasDisponiveisDTO(entregas);
    }


    public List<DadosEntregaDTO> entregasByIdEntregador(String userId) {
        Entregador entregador = entregadorRepository.findEntregadorByUsuario(userId);
        List<Entrega> entregas = entregaRepository.buscarEntregasByIdEntregador(entregador.getIdEntregador());
        return convertEntregasEntregador(entregas);
    }

    public static List<DadosEntregaDTO> convertDadosEntregaEntregasDisponiveisDTO(List<Entrega> result) {
        List<DadosEntregaDTO> entregaDTOS = new ArrayList<>();

        for (Entrega entrega : result) {
            DadosEntregaDTO dados = new DadosEntregaDTO();

            dados.setIdEntrega(entrega.getIdEntrega());
            dados.setCupomOrcamento(entrega.getCupomOrcamento());
            dados.setFormaPagamento(entrega.getFormaPagamento());
            dados.setIdUser(entrega.getUser().getId());
            dados.setTroco(entrega.getTroco());
            dados.setValorReceber(entrega.getValorReceber());
            dados.setValorTotal(entrega.getValorTotal());
            dados.setDataCadastroEntrega(entrega.getDataCadastroEntrega().toString());
            dados.setObservacao(entrega.getObservacao());
            dados.setStatusEntrega(entrega.getStatusEntrega());
            dados.setIdMatriz(entrega.getMatriz().getIdMatriz());
            entregaDTOS.add(dados);
        }
        return entregaDTOS;
    }

    public static List<DadosEntregaDTO> convertEntregasEntregador(List<Entrega> resultList) {
        List<DadosEntregaDTO> entregaDTOS = new ArrayList<>();

        for (Entrega entrega : resultList) {
            DadosEntregaDTO dados = new DadosEntregaDTO();

            dados.setIdEntrega(entrega.getIdEntrega());
            dados.setIdEntregador(entrega.getEntregador().getIdEntregador());
            dados.setCupomOrcamento(entrega.getCupomOrcamento());
            dados.setFormaPagamento(entrega.getFormaPagamento());
            dados.setIdUser(entrega.getUser().getId());
            dados.setTroco(entrega.getTroco());
            dados.setValorReceber(entrega.getValorReceber());
            dados.setValorTotal(entrega.getValorTotal());
            dados.setIdMatriz(entrega.getMatriz().getIdMatriz());
            if(entrega.getDataSelecaoEntrega()!=null) dados.setDataSelecaoEntrega(entrega.getDataSelecaoEntrega().toString());
            if(entrega.getDataCadastroEntrega()!=null) dados.setDataCadastroEntrega(entrega.getDataCadastroEntrega().toString());
            entregaDTOS.add(dados);
        }
        return entregaDTOS;
    }

    public ResponseEntity<?> buscarEntregas(Long idEntrega, LocalDateTime dataInicial, LocalDateTime dataFinal) {

        if(idEntrega != null){
         return ResponseEntity.ok(getDadosEntregaDTO(entregaRepository.buscarEntregaPorId(idEntrega)));
        }else {
            List<Entrega> entregas = entregaRepository.findEntregasByParams(dataInicial, dataFinal);
            return convertTodasEntregas(entregas);
        }
    }

    private  ResponseEntity<?> convertTodasEntregas(List<Entrega> entregas) {
        List<DadosEntregaDTO> entregaDTOS = new ArrayList<>();

        for (Entrega entrega : entregas) {
            DadosEntregaDTO dados = getDadosEntregaDTO(entrega);
            entregaDTOS.add(dados);
        }
        return  ResponseEntity.ok(entregaDTOS);
    }

    private static DadosEntregaDTO getDadosEntregaDTO(Entrega entrega) {
        DadosEntregaDTO dados = new DadosEntregaDTO();

        dados.setIdEntrega(entrega.getIdEntrega());
        if(entrega.getUserAssinatura() != null){
            dados.setIdUserAssinatura(entrega.getUserAssinatura().getId());
            dados.setNomeUserAssinatura(entrega.getUserAssinatura().getNome());
        }
        dados.setCupomOrcamento(entrega.getCupomOrcamento());
        dados.setFormaPagamento(entrega.getFormaPagamento());
        dados.setIdUser(entrega.getUser().getId());
        dados.setNomeUser(entrega.getUser().getNome());
        if(entrega.getEntregador() != null && entrega.getEntregador().getIdEntregador() != null){
            dados.setNomeEntregador(entrega.getEntregador().getNome());
            dados.setIdEntregador(entrega.getEntregador().getIdEntregador());
        }
        if(entrega.getMatriz() != null && entrega.getMatriz().getIdMatriz() != null){
            dados.setIdMatriz(entrega.getMatriz().getIdMatriz());
        }
        dados.setTroco(entrega.getTroco());
        dados.setValorReceber(entrega.getValorReceber());
        dados.setValorTotal(entrega.getValorTotal());
        if(entrega.getDataSelecaoEntrega() != null) dados.setDataSelecaoEntrega(entrega.getDataSelecaoEntrega().toString());
        if(entrega.getDataCadastroEntrega() != null) dados.setDataCadastroEntrega(entrega.getDataCadastroEntrega().toString());
        dados.setLatitude(entrega.getLatitude());
        dados.setLongitude(entrega.getLongitude());
        dados.setObservacao(entrega.getObservacao());
        dados.setStatusEntrega(entrega.getStatusEntrega());
        if(entrega.getDataFinalizacaoEntrega() != null) dados.setDataFinalizacaoEntrega(entrega.getDataFinalizacaoEntrega().toString());
        if(entrega.getDataAssinaturaEntrega() != null) dados.setDataAssinaturaEntrega(entrega.getDataAssinaturaEntrega().toString());
        return dados;
    }

    public void finalizarEntregaManual(Long idEntrega) {
        Entrega entrega = entregaRepository.findById(idEntrega).orElseThrow(()-> new EntityNotFoundException("Entrega não encontrada"));
        LocalDateTime dataAgora = LocalDateTime.now();
        entrega.setDataFinalizacaoEntrega(dataAgora);
        entrega.setStatusEntrega(StatusEntregaEnum.FINALIZADA_MANUAL.getDescricao());

    }

    public void cancelarEntrega(Long idEntrega) {
        Entrega entrega = entregaRepository.findById(idEntrega).orElseThrow(() -> new ServiceException("Entrega não encontrada"));
        if (StatusEntregaEnum.FINALIZADA.getDescricao().equals(entrega.getStatusEntrega()) ||
                StatusEntregaEnum.FINALIZADA_ASSINADA.getDescricao().equals(entrega.getStatusEntrega())
                || StatusEntregaEnum.FINALIZADA_MANUAL.getDescricao().equals(entrega.getStatusEntrega())) {
            throw new ServiceException("Entrega já assinada, não é permitido exclusão");
        }
        entrega.setStatusEntrega(StatusEntregaEnum.CANCELADA.getDescricao());
        entregaRepository.save(entrega);
    }

    public void salvarEntregaAvulsa(DadosEntregaAvulsaDTO dadosEntregaAvulsaDTO){
        EntregaAvulsa entregaAvulsa = new EntregaAvulsa();
        entregaAvulsa.setDataHoraInicioServico(dadosEntregaAvulsaDTO.getDataHoraInicioServico());
        entregaAvulsa.setDataHoraTerminoServico(dadosEntregaAvulsaDTO.getDataHoraTerminoServico());
        entregaAvulsa.setDescricaoServico(dadosEntregaAvulsaDTO.getDescricaoServico());
        entregaAvulsa.setObservacaoServico(dadosEntregaAvulsaDTO.getObservacaoServico());
        entregaAvulsa.setEntregador(entregadorRepository.findEntregadorByUsuario(dadosEntregaAvulsaDTO.getUserId()));
        entregaAvulsaRepository.save(entregaAvulsa);
    }


    public List<DadosEntregaAvulsaDTO> buscarTodasEntregasAvulsas() {

        List<EntregaAvulsa> entregas = entregaAvulsaRepository.findAll();
        return convertTodasEntregasAvulsas(entregas);
    }

    private List<DadosEntregaAvulsaDTO> convertTodasEntregasAvulsas(List<EntregaAvulsa> entregas) {
        List<DadosEntregaAvulsaDTO> dados = new ArrayList<>();
        for(EntregaAvulsa entrega : entregas){
            DadosEntregaAvulsaDTO dadosEntregaAvulsaDTO = new DadosEntregaAvulsaDTO();
            dadosEntregaAvulsaDTO.setDataHoraInicioServico(entrega.getDataHoraInicioServico());
            dadosEntregaAvulsaDTO.setDescricaoServico(entrega.getDescricaoServico());
            dadosEntregaAvulsaDTO.setDataHoraTerminoServico(entrega.getDataHoraTerminoServico());
            dadosEntregaAvulsaDTO.setObservacaoServico(entrega.getObservacaoServico());
            dadosEntregaAvulsaDTO.setUserId(entrega.getEntregador().getNome());
            dados.add(dadosEntregaAvulsaDTO);
        }
        return dados;
    }
}
