package br.com.starkstecnologia.control_api.repository;

import br.com.starkstecnologia.control_api.entity.Entrega;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long>, EntregaRepositoryCustom{
	
	@Query("SELECT e FROM Entrega e WHERE e.idEntrega IN (:idEntregas)")
	List<Entrega> buscarEntregasPorId(@Param("idEntregas") List<Long> dados);

	@Query("SELECT e FROM Entrega e WHERE e.idEntrega = :idEntrega")
	Entrega buscarEntregaPorId(@Param("idEntrega") Long idEntrega);

	@Query("SELECT e FROM Entrega e WHERE " +
			"(:dataInicio IS NULL OR e.dataCadastroEntrega >= :dataInicio) AND " +
			"(:dataTermino IS NULL OR e.dataCadastroEntrega <= :dataTermino) ")
	List<Entrega> findEntregasByParams(@Param("dataInicio") LocalDateTime dataInicio,
									   @Param("dataTermino") LocalDateTime dataTermino);

	@Query("SELECT e FROM Entrega e WHERE e.entregador IS NULL")
	List<Entrega> buscarEntregasDisponiveis();
	

	@Query("SELECT e FROM Entrega e WHERE e.entregador.idEntregador =:idEntregador AND" +
			" dataFinalizacaoEntrega IS NULL")
	List<Entrega> buscarEntregasByIdEntregador(Long idEntregador);

	@Query(value = "SELECT EXISTS (SELECT 1 FROM entrega WHERE id_entregador = :idEntregador)", nativeQuery = true)
	Boolean existeEntregaVinculadaEntregador(@Param("idEntregador") Long idEntregador);

//	@Query(value = "SELECT e.cupom_orcamento, " +
//			"u.nome AS nomeCaixa, " +
//			"e.data_cadastro_entrega, " +
//			"e.data_assinatura_entrega, " +
//			"TIMESTAMPDIFF(SECOND, e.data_cadastro_entrega, e.data_assinatura_entrega) AS mediaCaixa, " +
//			"en.nome AS nomeEntregador, " +
//			"e.data_selecao_entrega, " +
//			"e.data_finalizacao_entrega, " +
//			"TIMESTAMPDIFF(SECOND, e.data_selecao_entrega, e.data_finalizacao_entrega) AS mediaEntregador " +
//			"FROM Entrega e " +
//			"JOIN Usuario u ON e.id_user = u.id " +
//			"JOIN Entregador en ON e.id_entregador = en.id_entregador " +
//			"WHERE e.status_entrega = 'Finalizada' AND e.data_cadastro_entrega BETWEEN :dataInicio AND :dataFim", nativeQuery = true)
//	List<Tuple> buscarDadosTodasEntregas(LocalDateTime dataInicio, LocalDateTime dataFim, List<Long> idsEntregador);


//	@Query(value = "SELECT en.nome, " +
//			"COUNT(*) AS quantidadeEntregas, " +
//			"SUM(TIMESTAMPDIFF(SECOND, e.data_selecao_entrega, e.data_finalizacao_entrega)) AS totalTempoGasto, " +
//			"AVG(TIMESTAMPDIFF(SECOND, e.data_selecao_entrega, e.data_finalizacao_entrega)) AS mediaTempo, " +
//			"SUM(e.valor_total) AS valorTotalEntregas, " +
//			"SUM(e.troco) AS totalTroco " +
//			"FROM Entrega e " +
//			"JOIN Entregador en ON en.id_entregador = e.id_entregador " +
//			"WHERE e.data_selecao_entrega BETWEEN :dataInicio AND :dataFim " +
//			"GROUP BY e.id_entregador", nativeQuery = true)
//	List<Tuple> buscarDadosEntregasPorEntregador(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim, List<Long> idsEntregador);

}
