package com.bbm.register.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.bbm.register.model.enums.Cargo;

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

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date dataNasc;

	@Enumerated(EnumType.STRING)
	private Cargo cargoFunc;

	private String sexo;

	@Lob
	private byte[] curriculo;

	@OneToMany(mappedBy = "funcionario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<FuncionarioEndereco> endereco;

	@ManyToOne
	private Usuario boss;

	@ManyToOne
	private Profissao profissao;

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

	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public Cargo getCargoFunc() {
		return cargoFunc;
	}

	public void setCargoFunc(Cargo cargoFunc) {
		this.cargoFunc = cargoFunc;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public byte[] getCurriculo() {
		return curriculo;
	}

	public void setCurriculo(byte[] curriculo) {
		this.curriculo = curriculo;
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

	public Profissao getProfissao() {
		return profissao;
	}

	public void setProfissao(Profissao profissao) {
		this.profissao = profissao;
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
