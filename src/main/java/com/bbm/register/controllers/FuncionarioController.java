package com.bbm.register.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bbm.register.model.FuncionarioEndereco;
import com.bbm.register.reports.ReportUtil;
import com.bbm.register.model.Funcionario;
import com.bbm.register.repository.EnderecoRepository;
import com.bbm.register.repository.FuncionarioRepository;

@Controller
public class FuncionarioController {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ReportUtil reportUtil;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastroFuncionario")
	public ModelAndView init() {
		ModelAndView view = new ModelAndView("cadastros/cadastroFuncionario");

		view.addObject("usuarios", funcionarioRepository.findAll());
		view.addObject("usuario", new Funcionario());

		return view;
	}

	@RequestMapping(method = RequestMethod.POST, value = "**/salvarUsuario")
	public ModelAndView salvarUsuario(@Valid Funcionario funcionario, BindingResult bindingResult) {

		// Caso haja algum erro
		if (bindingResult.hasErrors()) {
			// Vai retornar para a mesma com a messagem de erro
			ModelAndView andView = new ModelAndView("cadastros/cadastroFuncionario");
			Iterable<Funcionario> funcionarios = funcionarioRepository.findAll();
			andView.addObject("usuarios", funcionarios);
			andView.addObject("usuario", funcionario);

			List<String> msg = new ArrayList<String>();

			// busca os erros e adiciona a respectiva messagem
			for (ObjectError error : bindingResult.getAllErrors()) {
				msg.add(error.getDefaultMessage());
			}

			// Coloca a messagem de erro na tela
			andView.addObject("msg", msg);
			return andView;

		} else {

			funcionarioRepository.save(funcionario);

			// Após salvar os dados irá exibir na tela
			ModelAndView view = new ModelAndView("cadastros/cadastroFuncionario");
			Iterable<Funcionario> usuarios = funcionarioRepository.findAll();
			view.addObject("usuarios", usuarios);
			view.addObject("usuario", new Funcionario());

			return view;
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listarUsuario")
	public ModelAndView listarUsuario() {
		ModelAndView view = new ModelAndView("cadastros/cadastroFuncionario");
		Iterable<Funcionario> usuarios = funcionarioRepository.findAll();
		view.addObject("usuarios", usuarios);
		view.addObject("usuario", new Funcionario());

		return view;
	}

	@GetMapping("/editarUsuario/{idUser}")
	public ModelAndView editarUsuario(@PathVariable("idUser") Long idUser) {
		Optional<Funcionario> usuario = funcionarioRepository.findById(idUser);

		ModelAndView view = new ModelAndView("cadastros/cadastroFuncionario");
		view.addObject("usuario", usuario.get());
		return view;
	}

	@GetMapping("/deletarUsuario/{idUser}")
	public ModelAndView deletarUsuario(@PathVariable("idUser") Long idUser) {
		funcionarioRepository.deleteById(idUser);

		ModelAndView view = new ModelAndView("cadastros/cadastroFuncionario");
		view.addObject("usuarios", funcionarioRepository.findAll());
		view.addObject("usuario", new Funcionario());

		return view;
	}

	@PostMapping("**/pesquisarUsuario")
	public ModelAndView pesquisarUsuario(@RequestParam("nomePesquisa") String nomePesquisa,
			@RequestParam("sexoPesquisa") String sexoPesquisa) {

		List<Funcionario> funcionarios = new ArrayList<Funcionario>();

		if ((nomePesquisa != null && !nomePesquisa.isEmpty()) && (sexoPesquisa != null && !sexoPesquisa.isEmpty())) {
			funcionarios = funcionarioRepository.findByNomeSexo(nomePesquisa.trim().toUpperCase(), sexoPesquisa);

		} else if (sexoPesquisa != null && !sexoPesquisa.isEmpty()) {
			funcionarios = funcionarioRepository.findBySexo(sexoPesquisa);
		} else {
			funcionarios = funcionarioRepository.findByNome(nomePesquisa.trim().toUpperCase());
		}

		ModelAndView view = new ModelAndView("cadastros/cadastroFuncionario");
		view.addObject("usuarios", funcionarios);
		view.addObject("usuario", new Funcionario());

		return view;
	}

	@GetMapping("/endereco/{idUser}")
	public ModelAndView endereco(@PathVariable("idUser") Long idUser) {
		Optional<Funcionario> usuario = funcionarioRepository.findById(idUser);

		ModelAndView view = new ModelAndView("cadastros/endereco");
		view.addObject("endereco", enderecoRepository.getEnderecos(idUser));
		view.addObject("usuario", usuario.get());

		return view;
	}

	@PostMapping("**/addEndereco/{idUser}")
	public ModelAndView addEndereco(FuncionarioEndereco endereco, @PathVariable("idUser") Long idUser) {
		Funcionario usuario = funcionarioRepository.findById(idUser).get();

		if (endereco != null && endereco.getDistrito().isEmpty() || endereco.getBairro().isEmpty()
				|| endereco.getTelefone().isEmpty()) {
			ModelAndView view = new ModelAndView("cadastros/endereco");
			view.addObject("usuario", usuario);
			view.addObject("endereco", enderecoRepository.getEnderecos(idUser));

			List<String> msg = new ArrayList<String>();
			if (endereco.getDistrito().isEmpty()) {
				msg.add("Preencha o Campo Distrito!");

			} else if (endereco.getBairro().isEmpty()) {
				msg.add("Preencha o Campo Bairro");

			} else if (endereco.getTelefone().isEmpty()) {
				msg.add("Preencha o Campo Telefone");
			}

			view.addObject("msg", msg);

			return view;
		} else {

			endereco.setUsuario(usuario);
			enderecoRepository.save(endereco);

			ModelAndView view = new ModelAndView("cadastros/endereco");
			view.addObject("usuario", usuario);
			view.addObject("endereco", enderecoRepository.getEnderecos(idUser));

			return view;
		}
	}

	@GetMapping("/deletarEndereco/{idEnder}")
	public ModelAndView deletarEndereco(@PathVariable("idEnder") Long idEnder) {
		Funcionario usuario = enderecoRepository.findById(idEnder).get().getUsuario();

		enderecoRepository.deleteById(idEnder);

		ModelAndView view = new ModelAndView("cadastros/endereco");
		view.addObject("usuario", usuario);
		view.addObject("endereco", enderecoRepository.getEnderecos(usuario.getId()));

		return view;
	}

	@GetMapping("**/pesquisarUsuario")
	public void imprimeRelatorio(@RequestParam("nomePesquisa") String nomePesquisa,
			@RequestParam("sexoPesquisa") String sexoPesquisa, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		List<Funcionario> funcionarios = new ArrayList<Funcionario>();

		if ((nomePesquisa != null && !nomePesquisa.isEmpty()) && (sexoPesquisa != null && !sexoPesquisa.isEmpty())) {
			funcionarios = funcionarioRepository.findByNomeSexo(nomePesquisa.trim().toUpperCase(), sexoPesquisa);

		} else if (sexoPesquisa != null && !sexoPesquisa.isEmpty()) {
			funcionarios = funcionarioRepository.findBySexo(sexoPesquisa);

		} else if (nomePesquisa != null && !nomePesquisa.isEmpty()) {
			funcionarios = funcionarioRepository.findByNome(nomePesquisa.trim().toUpperCase());

		} else {
			funcionarios = funcionarioRepository.findAll();
		}

		// Chama o metodo para a geracão do relatório
		byte[] report = reportUtil.geraRelatorio(funcionarios, "funcionario", request.getServletContext());

		// Tamanho da Resposta ao navegador
		response.setContentLength(report.length);

		// Define o tipo de resposta ao navegador
		response.setContentType("application/octet-stream");

		// Define o cabecalho da resposta ao navegador
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", "Relatório de Funcionários.pdf");
		response.setHeader(headerKey, headerValue);

		// Finaliza a resposta ao navegador
		response.getOutputStream().write(report);
	}
}
