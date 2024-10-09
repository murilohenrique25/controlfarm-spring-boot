package br.com.starkstecnologia.control_api.repository;

import br.com.starkstecnologia.control_api.entity.Matriz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatrizRepository extends JpaRepository<Matriz, Long> {
}
