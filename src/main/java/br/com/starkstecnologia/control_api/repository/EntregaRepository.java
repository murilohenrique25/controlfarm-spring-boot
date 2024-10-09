package br.com.starkstecnologia.control_api.repository;

import br.com.starkstecnologia.control_api.entity.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long>{
	
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

}
