package br.com.starkstecnologia.control_api.repository;

import br.com.starkstecnologia.control_api.entity.Entregador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EntregadorRepository extends JpaRepository<Entregador, Long>{
	
	@Query("SELECT e FROM Entregador e WHERE e.usuario =:usuario")
	 Entregador logarFilter(@Param("usuario") String userId);

	@Query("SELECT COUNT(e) > 0 FROM Entregador e WHERE e.usuario = :usuario")
	   boolean verificaUsuarioJaExistente(@Param("usuario") String usuario);

	@Query("SELECT e FROM Entregador e WHERE e.usuario=:usuario")
	    Entregador findEntregadorByUsuario(@Param("usuario") String userId);

	@Query("SELECT e FROM Entregador e WHERE e.usuario = :userId")
	 Entregador logarApp(@Param("userId") String userId);

	@Query(value =  "SELECT COUNT(*) AS quantidade_entregas " +
	                    "FROM entrega JOIN entregador ON entrega.id_entregador = entregador.id_entregador " +
	                    "WHERE EXTRACT(MONTH FROM data_finalizacao_entrega) = EXTRACT(MONTH FROM CURRENT_DATE) " +
	                    "AND entregador.usuario = :usuario", nativeQuery = true)
	  public int quantidadeEntregasFinalizadasMes(@Param("usuario") String userId);

}
