package br.com.starkstecnologia.control_api.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class EntregaRepositoryCustomImpl implements EntregaRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Tuple> buscarDadosEntregasPorEntregador(LocalDateTime dataInicio, LocalDateTime dataFim, List<Long> idsEntregador) {
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT en.nome AS nome, " +
                        "COUNT(e) AS quantidadeEntregas, " +
                        "SUM(TIMESTAMPDIFF(SECOND, e.dataSelecaoEntrega, e.dataFinalizacaoEntrega)) AS totalTempoGasto, " +
                        "AVG(TIMESTAMPDIFF(SECOND, e.dataSelecaoEntrega, e.dataFinalizacaoEntrega)) AS mediaTempo, " +
                        "SUM(e.valorTotal) AS valorTotalEntregas, " +
                        "SUM(e.troco) AS totalTroco " +
                        "FROM Entrega e " +
                        "JOIN e.entregador en " +
                        "WHERE e.dataSelecaoEntrega BETWEEN :dataInicio AND :dataFim " +
                        "AND (e.statusEntrega = 'Finalizada' OR e.statusEntrega = 'Finalizada Manualmente' OR e.statusEntrega = 'Finalizada e Assinada' )");

        if (idsEntregador != null && !idsEntregador.isEmpty()) {
            queryBuilder.append(" AND en.id IN :idsEntregador");
        }

        queryBuilder.append(" GROUP BY e.entregador.id");

        Query query = entityManager.createQuery(queryBuilder.toString(), Tuple.class);
        query.setParameter("dataInicio", dataInicio);
        query.setParameter("dataFim", dataFim);

        // Define o parâmetro idsEntregador apenas se a lista não estiver vazia
        if (idsEntregador != null && !idsEntregador.isEmpty()) {
            query.setParameter("idsEntregador", idsEntregador);
        }

        return query.getResultList();
    }

    @Override
    public List<Tuple> buscarDadosTodasEntregas(LocalDateTime dataInicio, LocalDateTime dataFim, List<Long> idsEntregador, String statusEntrega) {
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT e.cupomOrcamento AS cupomOrcamento, " +
                        "u.nome AS nomeCaixa, " +
                        "us.nome AS nomeCaixaAssinatura, " +
                        "e.dataCadastroEntrega AS dataCadastroEntrega, " +
                        "e.dataAssinaturaEntrega AS dataAssinaturaEntrega, " +
                        "TIMESTAMPDIFF(SECOND, e.dataCadastroEntrega, e.dataAssinaturaEntrega) AS mediaCaixa, " +
                        "en.nome AS nomeEntregador, " +
                        "e.dataSelecaoEntrega AS dataSelecaoEntrega, " +
                        "e.dataFinalizacaoEntrega AS dataFinalizacaoEntrega, " +
                        "TIMESTAMPDIFF(SECOND, e.dataSelecaoEntrega, e.dataFinalizacaoEntrega) AS mediaEntregador " +
                        "FROM Entrega e " +
                        "JOIN Usuario u ON e.user.id = u.id " +
                        "LEFT JOIN Usuario us ON e.userAssinatura.id = u.id " +
                        "LEFT JOIN Entregador en ON e.entregador.idEntregador = en.idEntregador " +
                        "WHERE e.statusEntrega = :statusEntrega " +
                        "AND e.dataCadastroEntrega BETWEEN :dataInicio AND :dataFim");

        // Adiciona a condição de idsEntregador se a lista não estiver vazia
        if (idsEntregador != null && !idsEntregador.isEmpty()) {
            queryBuilder.append(" AND en.id IN :idsEntregador");
        }

        Query query = entityManager.createQuery(queryBuilder.toString(), Tuple.class);
        query.setParameter("dataInicio", dataInicio);
        query.setParameter("dataFim", dataFim);
        query.setParameter("statusEntrega", statusEntrega);

        // Define o parâmetro idsEntregador apenas se a lista não estiver vazia
        if (idsEntregador != null && !idsEntregador.isEmpty()) {
            query.setParameter("idsEntregador", idsEntregador);
        }

        return query.getResultList();
    }

    @Override
    public Tuple buscarDadosEntregasPorEntregadorApp(LocalDateTime dataInicio, LocalDateTime dataFim, String userId) {
        String queryBuilder = "SELECT COUNT(e) AS quantidadeEntregas, " +
                "AVG(TIMESTAMPDIFF(SECOND, e.dataSelecaoEntrega, e.dataFinalizacaoEntrega)) AS mediaTempo " +
                "FROM Entrega e " +
                "JOIN e.entregador en " +
                "WHERE e.dataSelecaoEntrega BETWEEN :dataInicio AND :dataFim AND en.usuario = :userId " +
                "AND (e.statusEntrega = 'Finalizada' OR e.statusEntrega = 'Finalizada Manualmente' OR e.statusEntrega = 'Finalizada e Assinada' )";
        Query query = entityManager.createQuery(queryBuilder, Tuple.class);
        query.setParameter("dataInicio", dataInicio);
        query.setParameter("dataFim", dataFim);

        query.setParameter("userId", userId);
        return (Tuple) query.getSingleResult();
    }

    @Override
    public  List<Tuple> buscarDadosEntregasPorNaoFinalizadasNoDiaApp(LocalDateTime dataInicio, LocalDateTime dataFim) {
        String queryBuilder = "SELECT e.cupomOrcamento, e.statusEntrega, e.dataCadastroEntrega " +
                "FROM Entrega e " +
                "WHERE e.dataCadastroEntrega BETWEEN :dataInicio AND :dataFim " +
                "AND e.statusEntrega = 'Aberta' OR e.statusEntrega = 'Em rota'";
        Query query = entityManager.createQuery(queryBuilder, Tuple.class);
        query.setParameter("dataInicio", dataInicio);
        query.setParameter("dataFim", dataFim);

        return query.getResultList();
    }
}
