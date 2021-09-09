package com.bbm.register.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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

	private String sexo;

	@OneToMany(mappedBy = "funcionario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<FuncionarioEndereco> endereco;

	@ManyToOne
	private Usuario boss;

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

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public List<FuncionarioEndereco> getEndereco() {
		return endereco;
	}

	public void setEndereco(List<FuncionarioEndereco> endereco) {
		this.endereco = endereco;
	}

	public Usuario getUsuario() {
		return boss;
	}

	public void setUsuario(Usuario boss) {
		this.boss = boss;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bi, cargoFunc, email, endereco, id, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Funcionario other = (Funcionario) obj;
		return Objects.equals(bi, other.bi) && Objects.equals(cargoFunc, other.cargoFunc)
				&& Objects.equals(email, other.email) && Objects.equals(endereco, other.endereco)
				&& Objects.equals(id, other.id) && Objects.equals(nome, other.nome);
	}

}
