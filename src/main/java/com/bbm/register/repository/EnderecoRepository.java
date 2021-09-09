package com.bbm.register.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bbm.register.model.FuncionarioEndereco;

@Repository
@Transactional
public interface EnderecoRepository extends JpaRepository<FuncionarioEndereco, Long> {

	@Query("select e from FuncionarioEndereco e where e.funcionario.id = ?1")
	public List<FuncionarioEndereco> getEnderecos(Long id);
}
