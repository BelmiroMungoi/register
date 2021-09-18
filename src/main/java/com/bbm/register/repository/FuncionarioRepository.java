package com.bbm.register.repository;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bbm.register.model.Funcionario;

@Repository
@Transactional
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	@Query("select u from Funcionario u where upper(trim(u.nome)) like %?1% ")
	List<Funcionario> findByNome(String nome);

	@Query("select u from Funcionario u where upper(trim(u.nome)) like %?1% and sexo = ?2")
	List<Funcionario> findByNomeSexo(String nome, String sexo);

	@Query("select u from Funcionario u where u.sexo = ?1")
	List<Funcionario> findBySexo(String sexo);

	// Metodo para consulta de funcionarios com uma paginacao, vai trazer 5 funcionarios por vez
	default Page<Funcionario> findByName(String nome, Pageable pageable) {

		Funcionario funcionario = new Funcionario();
		funcionario.setNome(nome);

		// Configurando a pesquisa para consultar por partes do nome(like no SQL)
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().withMatcher("nome",
				ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

		// Une o objecto com o valor e configura a consulta
		Example<Funcionario> example = Example.of(funcionario, exampleMatcher);

		Page<Funcionario> funcionarios = findAll(example, pageable);

		return funcionarios;
	}

	// Metodo de consulta de funcionarios por sexo com paginacao
	default Page<Funcionario> findBySex(String sexo, Pageable pageable) {

		Funcionario funcionario = new Funcionario();
		funcionario.setSexo(sexo);

		// Configurando a pesquisa para consultar por partes do nome(like no SQL)
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().withMatcher("sexo",
				ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

		// Une o objecto com o valor e configura a consulta
		Example<Funcionario> example = Example.of(funcionario, exampleMatcher);

		Page<Funcionario> funcionarios = findAll(example, pageable);

		return funcionarios;
	}

	//Metodo de consulta de funcionarios por sexo e nome com paginacao
	default Page<Funcionario> findByNameSex(String nome, String sexo, Pageable pageable) {

		Funcionario funcionario = new Funcionario();
		funcionario.setNome(nome);
		funcionario.setSexo(sexo);

		// Configurando a pesquisa para consultar por partes do nome(like no SQL)
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
				.withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withMatcher("sexo", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

		// Une o objecto com o valor e configura a consulta
		Example<Funcionario> example = Example.of(funcionario, exampleMatcher);

		Page<Funcionario> funcionarios = findAll(example, pageable);

		return funcionarios;
	}
}
