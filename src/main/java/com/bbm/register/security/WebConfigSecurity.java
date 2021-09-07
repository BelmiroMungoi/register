package com.bbm.register.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.bbm.register.service.ImplementsUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{

	@Autowired
	private ImplementsUserDetailsService detailsService;
	
	@Override // Configura as solitacões de acesso por Http
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
		.disable() // Desativa as configuracões padrão de memoria
		.authorizeRequests() // Permite restringir acesso
		
		//.antMatchers(HttpMethod.GET, "/login").permitAll() // Qualquer usuario pode acessar a pagina de login
		//.antMatchers(HttpMethod.GET, "/cadastroFuncionario").hasAnyRole("ADMIN")
		
		.anyRequest().authenticated() // Entra na autenticacão
		.and().formLogin().permitAll() // Cria o formulario de login e permite o acesso
		.loginPage("/login")
		.defaultSuccessUrl("/")
		.failureUrl("/login?error=true")
		.and().logout().logoutSuccessUrl("/login") // Para sair do sistema, vai mapeiar a url de logout e invalidar o usuario autenticado
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	@Override // Cria a autenticacão de usuário com o banco de dados ou em memória
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(detailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
		
		/* 
		 * Autenticacao de usuario em memoria
		auth.inMemoryAuthentication()
		.passwordEncoder(new BCryptPasswordEncoder())// Para criptografar a password
		.withUser("belmiro") // Define o usuario
		.password("$2a$10$VI1Rpl7OcsefCzN6F0M/.uz3iIIb9laBm.Yukex/AFlvFIHFnvflK") // Define a senha do usuario
		.roles("ADMIN"); // Define o nivel de acesso ou perfil de usuario
		*/
	}
	
	@Override // Ignora URL especificas
	public void configure(WebSecurity web) throws Exception {
	
	}
}
