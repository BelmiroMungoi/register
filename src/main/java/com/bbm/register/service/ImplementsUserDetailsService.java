package com.bbm.register.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bbm.register.model.Usuario;
import com.bbm.register.repository.UsuarioRepository;

@Service
public class ImplementsUserDetailsService implements UserDetailsService{

	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = repository.findByUserName(username);
		
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário não encontrado!!!");
		}
		return usuario;
	}

}
