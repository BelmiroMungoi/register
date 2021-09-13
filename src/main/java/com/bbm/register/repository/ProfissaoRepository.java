package com.bbm.register.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bbm.register.model.Profissao;

@Repository
@Transactional
public interface ProfissaoRepository extends JpaRepository<Profissao, Long>{

}
