package com.bbm.register.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bbm.register.model.Funcionario;

@Repository
@Transactional
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

	@Query("select u from Funcionario u where upper(trim(u.nome)) like %?1% ")
	List<Funcionario> findByNome(String nome);
}
