package com.bbm.register.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Insira o nome de Usuário!!!")
	@NotEmpty(message = "Insira o nome de Usuário!!!")
	private String userName;

	@NotNull(message = "Insira a Palavra-Passe!!!")
	@NotEmpty(message = "Insira a Palavra-Passe!!!")
	private String passWord;

	@OneToMany(mappedBy = "boss", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Funcionario> funcionario;

	@OneToMany
	@JoinTable(name = "usuario_roles", 
		joinColumns = @JoinColumn(name = "usuario.id",
			referencedColumnName = "id",
			table = "usuario"),
		
		inverseJoinColumns = @JoinColumn(name = "roles_id",
			referencedColumnName = "id",
			table = "roles"))
	private List<Roles> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return userName;
	}

	public void setName(String name) {
		this.userName = name;
	}

	public String getPass() {
		return passWord;
	}

	public void setPass(String passWord) {
		this.passWord = passWord;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return passWord;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
