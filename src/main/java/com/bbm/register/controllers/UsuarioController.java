package com.bbm.register.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bbm.register.model.UsuarioEntity;
import com.bbm.register.repository.UsuarioRepository;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = "/cadastroUsuario")
	public String init() {
		return "cadastros/cadastroUsuario";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/salvarUsuario")
	public String salvarUsuario(UsuarioEntity usuario) {
		usuarioRepository.save(usuario);
		return "cadastros/cadastroUsuario";
	}
}
