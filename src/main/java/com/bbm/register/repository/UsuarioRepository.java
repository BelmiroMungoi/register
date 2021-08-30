package com.bbm.register.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bbm.register.model.UsuarioEntity;

@Repository
@Transactional
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>{

}
