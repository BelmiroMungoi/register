package com.bbm.register.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bbm.register.model.Funcionario;
import com.bbm.register.model.FuncionarioEndereco;
import com.bbm.register.reports.ReportUtil;
import com.bbm.register.repository.EnderecoRepository;
import com.bbm.register.repository.FuncionarioRepository;
import com.bbm.register.repository.ProfissaoRepository;

@Controller
public class FuncionarioController {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ReportUtil reportUtil;

	@Autowired
	private ProfissaoRepository profissaoRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastroFuncionario")
	public ModelAndView init() {
		ModelAndView view = new ModelAndView("cadastros/cadastroFuncionario");
		// Vai carregar somente 5 funcionarios ao iniciar
		view.addObject("usuarios", funcionarioRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		view.addObject("profissoes", profissaoRepository.findAll());
		view.addObject("usuario", new Funcionario());

		return view;
	}

	@GetMapping("/funcionariosPag")
	public ModelAndView loadWithPag(@PageableDefault(size = 5, sort = "nome") Pageable pageable, ModelAndView view,
			@RequestParam("nomePesquisa") String nomePesquisa) {

		Page<Funcionario> page = funcionarioRepository.findAll(pageable);
		view.addObject("usuarios", page);
		view.addObject("usuario", new Funcionario());
		view.setViewName("cadastros/cadastroFuncionario");
		
		return view;
	}

	@RequestMapping(method = RequestMethod.POST, value = "**/salvarUsuario", consumes = { "multipart/form-data" })
	public ModelAndView salvarUsuario(@Valid Funcionario funcionario, BindingResult bindingResult, MultipartFile file)
			throws IOException {

		// Caso haja algum erro
		if (bindingResult.hasErrors()) {
			// Vai retornar para a mesma com a messagem de erro
			ModelAndView andView = new ModelAndView("cadastros/cadastroFuncionario");
			andView.addObject("usuarios", funcionarioRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
			andView.addObject("profissoes", profissaoRepository.findAll());
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
			// Verificando se existe um ficheiro para gravacao
			if (file.getSize() > 0) {
				funcionario.setCurriculo(file.getBytes());
				funcionario.setFileType(file.getContentType());
				funcionario.setOriginalFileName(file.getOriginalFilename());

				/*
				 * Caso esteja em edicao e ja exista um curriculo associado ao usuario Pega o
				 * curriculo existente e guarda
				 */
			} else if (funcionario.getId() != null && funcionario.getId() > 0) {
				Funcionario temp = funcionarioRepository.findById(funcionario.getId()).get();
				funcionario.setCurriculo(temp.getCurriculo());
				funcionario.setFileType(temp.getFileType());
				funcionario.setOriginalFileName(temp.getOriginalFileName());
			}
			funcionarioRepository.save(funcionario);

			// Após salvar os dados irá exibir na tela
			ModelAndView view = new ModelAndView("cadastros/cadastroFuncionario");
			view.addObject("usuarios", funcionarioRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
			view.addObject("profissoes", profissaoRepository.findAll());
			view.addObject("ms", "Funcionário Salvo Com Sucesso!");
			view.addObject("usuario", new Funcionario());

			return view;
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listarUsuario")
	public ModelAndView listarUsuario() {
		ModelAndView view = new ModelAndView("cadastros/cadastroFuncionario");
		view.addObject("usuarios", funcionarioRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		view.addObject("profissoes", profissaoRepository.findAll());
		view.addObject("usuario", new Funcionario());

		return view;
	}

	@GetMapping("/editarUsuario/{idUser}")
	public ModelAndView editarUsuario(@PathVariable("idUser") Long idUser) {
		Optional<Funcionario> usuario = funcionarioRepository.findById(idUser);

		ModelAndView view = new ModelAndView("cadastros/cadastroFuncionario");
		view.addObject("usuarios", funcionarioRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		view.addObject("profissoes", profissaoRepository.findAll());
		view.addObject("usuario", usuario.get());
		return view;
	}

	@GetMapping("/deletarUsuario/{idUser}")
	public ModelAndView deletarUsuario(@PathVariable("idUser") Long idUser) {
		funcionarioRepository.deleteById(idUser);

		ModelAndView view = new ModelAndView("cadastros/cadastroFuncionario");
		view.addObject("usuarios", funcionarioRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		view.addObject("profissoes", profissaoRepository.findAll());
		view.addObject("usuario", new Funcionario());

		return view;
	}

	@PostMapping("**/pesquisarUsuario")
	public ModelAndView pesquisarUsuario(@RequestParam("nomePesquisa") String nomePesquisa,
			@RequestParam("sexoPesquisa") String sexoPesquisa,
			@PageableDefault(size = 5, sort = "nome") Pageable pageable) {

		Page<Funcionario> funcionarios = null;

		if ((nomePesquisa != null && !nomePesquisa.isEmpty()) && (sexoPesquisa != null && !sexoPesquisa.isEmpty())) {
			funcionarios = funcionarioRepository.findByNameSex(nomePesquisa, sexoPesquisa, pageable);

		} else if (sexoPesquisa != null && !sexoPesquisa.isEmpty()) {
			funcionarios = funcionarioRepository.findBySex(sexoPesquisa, pageable);

		} else {
			funcionarios = funcionarioRepository.findByName(nomePesquisa, pageable);
		}

		ModelAndView view = new ModelAndView("cadastros/cadastroFuncionario");
		view.addObject("usuarios", funcionarios);
		view.addObject("profissoes", profissaoRepository.findAll());
		view.addObject("usuario", new Funcionario());
		view.addObject("nomePesquisa", nomePesquisa);

		return view;
	}

	@GetMapping("/endereco/{idUser}")
	public ModelAndView endereco(@PathVariable("idUser") Long idUser) {
		Optional<Funcionario> usuario = funcionarioRepository.findById(idUser);

		ModelAndView view = new ModelAndView("cadastros/endereco");
		view.addObject("endereco", enderecoRepository.getEnderecos(idUser));
		view.addObject("profissoes", profissaoRepository.findAll());
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
			view.addObject("profissoes", profissaoRepository.findAll());
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
			view.addObject("ms", "Endereco Salvo Com Sucesso!");
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

	@GetMapping("**/baixarCurriculo/{idUser}")
	public void baixarCurriculo(@PathVariable("idUser") Long idUser, HttpServletResponse response) throws IOException {

		Funcionario funcionario = funcionarioRepository.findById(idUser).get();

		// Verifica se existe o curriculo para o download
		if (funcionario.getCurriculo() != null) {

			// Seta o tamanho da resposta
			response.setContentLength(funcionario.getCurriculo().length);

			// Seta o tipo do arquivo para o download
			response.setContentType(funcionario.getFileType());

			// Seta o cabecalho da resposta
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", funcionario.getOriginalFileName());
			response.setHeader(headerKey, headerValue);

			response.getOutputStream().write(funcionario.getCurriculo());
		}
	}
}
