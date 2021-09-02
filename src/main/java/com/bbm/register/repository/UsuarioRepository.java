package com.bbm.register.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bbm.register.model.UsuarioEntity;

@Repository
@Transactional
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>{

	@Query("select u from UsuarioEntity u where upper(trim(u.nome)) like %?1% ")
	List<UsuarioEntity> findByNome(String nome);
}
