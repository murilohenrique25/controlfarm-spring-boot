package br.com.starkstecnologia.control_api.repository;

import jakarta.persistence.Tuple;

import java.time.LocalDateTime;
import java.util.List;

public interface EntregaRepositoryCustom {
    List<Tuple> buscarDadosEntregasPorEntregador(LocalDateTime dataInicio, LocalDateTime dataFim, List<Long> idsEntregador);
    List<Tuple> buscarDadosTodasEntregas(LocalDateTime dataInicio, LocalDateTime dataFim, List<Long> idsEntregador, String statusEntrega);
    Tuple buscarDadosEntregasPorEntregadorApp(LocalDateTime dataInicio, LocalDateTime dataFim, String userId);
    List<Tuple> buscarDadosEntregasPorNaoFinalizadasNoDiaApp(LocalDateTime dataInicio, LocalDateTime dataFim);
}
