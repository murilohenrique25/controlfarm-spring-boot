package br.com.starkstecnologia.control_api.repository;

import br.com.starkstecnologia.control_api.entity.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long>{
	
	@Query("SELECT e FROM Entrega e WHERE e.idEntrega IN (:idEntregas)")
	List<Entrega> buscarEntregasPorId(@Param("idEntregas") List<Long> dados);
	
	@Query(value = "SELECT id_entrega, data_cadastro_entrega, data_assinatura_entrega, data_selecao_entrega, "
			+ "                       data_finalizacao_entrega, id_caixa, latitude, longitude, "
			+ "                      valor_total, valor_receber, troco, cupom_orcamento, tipo_entrega, forma_pagamento "
			+ "                FROM entrega WHERE id_entregador IS NULL", nativeQuery = true)
	List<Entrega> buscarEntregasDisponiveis();
	
	@Query(value = "SELECT id_entrega, data_cadastro_entrega, data_assinatura_entrega, data_selecao_entrega, " +
                "       data_finalizacao_entrega, id_caixa, latitude, longitude, id_entregador, " +
                "       valor_total, valor_receber, troco, cupom_orcamento, tipo_entrega, forma_pagamento " +
                "FROM entrega WHERE id_entregador =:idEntregador" +
                " AND data_finalizacao_entrega IS NULL", nativeQuery = true)
	List<Entrega> buscarEntregasByIdEntregador(Long idEntregador);

	@Query(value = "SELECT EXISTS (SELECT 1 FROM entrega WHERE id_entregador = :idEntregador)", nativeQuery = true)
	Boolean existeEntregaVinculadaEntregador(@Param("idEntregador") Long idEntregador);

}
