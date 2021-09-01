package com.bbm.register.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView salvarUsuario(UsuarioEntity usuario) {
		usuarioRepository.save(usuario);
		// Após salvar os dados irá exibir na tela
		ModelAndView view = new ModelAndView("cadastros/cadastroUsuario");
		Iterable<UsuarioEntity> usuarios = usuarioRepository.findAll();
		view.addObject("usuarios", usuarios);
		return view;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listarUsuario")
	public ModelAndView listarUsuario() {
		ModelAndView view = new ModelAndView("cadastros/cadastroUsuario");
		Iterable<UsuarioEntity> usuarios = usuarioRepository.findAll();
		view.addObject("usuarios", usuarios);
		return view;
	}

	@GetMapping("/editarUsuario/{idUser}")
	public ModelAndView editarUsuario(@PathVariable("idUser") Long idUser) {
		Optional<UsuarioEntity> usuario = usuarioRepository.findById(idUser);
		
		ModelAndView view = new ModelAndView("cadastros/cadastroUsuario");
		view.addObject("usuario", usuario.get());
		return view;
	}
}
