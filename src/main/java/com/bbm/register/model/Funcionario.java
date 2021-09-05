package com.bbm.register.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Funcionario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Nome não pode ser nulo! Insira o Nome.")
	@NotEmpty(message = "Nome não pode ser vazio! Insira o Nome.")
	private String nome;
	
	@NotNull(message = "BI não pode ser nulo! Insira o BI.")
	@NotEmpty(message = "BI não pode ser vazio! Insira o BI.")
	private String bi;
	
	@NotNull(message = "Email não pode ser nulo! Insira o Email.")
	@NotEmpty(message = "Email não pode ser vazio! Insira o Email.")
	@Email(message = "Email inválido")
	private String email;
	
	@NotNull(message = "Cargo não pode ser nulo! Insira o Cargo.")
	@NotEmpty(message = "Cargo não pode ser vazio! Insira a Cargo.")
	private String cargoFunc;

	@OneToMany(mappedBy = "usuario", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Endereco> endereco;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getBi() {
		return bi;
	}

	public void setBi(String bi) {
		this.bi = bi;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCargoFunc() {
		return cargoFunc;
	}

	public void setCargoFunc(String cargoFunc) {
		this.cargoFunc = cargoFunc;
	}

	public List<Endereco> getEndereco() {
		return endereco;
	}

	public void setEndereco(List<Endereco> endereco) {
		this.endereco = endereco;
	}

}
