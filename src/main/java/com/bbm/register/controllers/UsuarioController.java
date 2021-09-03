package com.bbm.register.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bbm.register.model.Endereco;
import com.bbm.register.model.UsuarioEntity;
import com.bbm.register.repository.EnderecoRepository;
import com.bbm.register.repository.UsuarioRepository;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastroUsuario")
	public ModelAndView init() {
		ModelAndView view = new ModelAndView("cadastros/cadastroUsuario");

		view.addObject("usuarios", usuarioRepository.findAll());
		view.addObject("usuario", new UsuarioEntity());

		return view;
	}

	@RequestMapping(method = RequestMethod.POST, value = "**/salvarUsuario")
	public ModelAndView salvarUsuario(UsuarioEntity usuario) {
		usuarioRepository.save(usuario);
		// Após salvar os dados irá exibir na tela
		ModelAndView view = new ModelAndView("cadastros/cadastroUsuario");
		Iterable<UsuarioEntity> usuarios = usuarioRepository.findAll();
		view.addObject("usuarios", usuarios);
		view.addObject("usuario", new UsuarioEntity());

		return view;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listarUsuario")
	public ModelAndView listarUsuario() {
		ModelAndView view = new ModelAndView("cadastros/cadastroUsuario");
		Iterable<UsuarioEntity> usuarios = usuarioRepository.findAll();
		view.addObject("usuarios", usuarios);
		view.addObject("usuario", new UsuarioEntity());

		return view;
	}

	@GetMapping("/editarUsuario/{idUser}")
	public ModelAndView editarUsuario(@PathVariable("idUser") Long idUser) {
		Optional<UsuarioEntity> usuario = usuarioRepository.findById(idUser);

		ModelAndView view = new ModelAndView("cadastros/cadastroUsuario");
		view.addObject("usuario", usuario.get());
		return view;
	}

	@GetMapping("/deletarUsuario/{idUser}")
	public ModelAndView deletarUsuario(@PathVariable("idUser") Long idUser) {
		usuarioRepository.deleteById(idUser);

		ModelAndView view = new ModelAndView("cadastros/cadastroUsuario");
		view.addObject("usuarios", usuarioRepository.findAll());
		view.addObject("usuario", new UsuarioEntity());

		return view;
	}

	@PostMapping("**/pesquisarUsuario")
	public ModelAndView pesquisarUsuario(@RequestParam("nomePesquisa") String nomePesquisa) {

		ModelAndView view = new ModelAndView("cadastros/cadastroUsuario");
		view.addObject("usuarios", usuarioRepository.findByNome(nomePesquisa.trim().toUpperCase()));
		view.addObject("usuario", new UsuarioEntity());

		return view;
	}

	@GetMapping("/endereco/{idUser}")
	public ModelAndView endereco(@PathVariable("idUser") Long idUser) {
		Optional<UsuarioEntity> usuario = usuarioRepository.findById(idUser);

		ModelAndView view = new ModelAndView("cadastros/endereco");
		view.addObject("endereco", enderecoRepository.getEnderecos(idUser));
		view.addObject("usuario", usuario.get());
		
		return view;
	}

	@PostMapping("**/addEndereco/{idUser}")
	public ModelAndView addEndereco(Endereco endereco, @PathVariable("idUser") Long idUser) {
		
		UsuarioEntity usuario = usuarioRepository.findById(idUser).get();
		endereco.setUsuario(usuario);
		enderecoRepository.save(endereco);

		ModelAndView view = new ModelAndView("cadastros/endereco");
		view.addObject("usuario", usuario);
		view.addObject("endereco", enderecoRepository.getEnderecos(idUser));
				
		return view;
	}
	
	@GetMapping("/deletarEndereco/{idEnder}")
	public ModelAndView deletarEndereco(@PathVariable("idEnder") Long idEnder) {
		UsuarioEntity usuario = enderecoRepository.findById(idEnder)
				.get().getUsuario();
		
		enderecoRepository.deleteById(idEnder);

		ModelAndView view = new ModelAndView("cadastros/endereco");
		view.addObject("usuario", usuario);
		view.addObject("endereco", enderecoRepository.getEnderecos(usuario.getId()));

		return view;
	}
}
