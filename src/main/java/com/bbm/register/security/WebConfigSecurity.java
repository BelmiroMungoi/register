package com.bbm.register.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{

	@Override // Configura as solitacões de acesso por Http
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
		.disable() // Desativa as configuracões padrão de memoria
		.authorizeRequests() // Permite restringir acesso
		.antMatchers(HttpMethod.GET, "/").permitAll() // Qualquer usuario pode acessar a pagina inicial
		.anyRequest().authenticated() // Entra na autenticacão
		.and().formLogin().permitAll() // Permite o acesso a qualquer usuario
		.and().logout() // Para sair do sistema, vai mapeiar a url de logout e invalidar o usuario autenticado
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	@Override // Cria a autenticacão de usuário com o banco de dados ou em memória
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		.passwordEncoder(NoOpPasswordEncoder.getInstance())// Para nao criptografar a password
		.withUser("belmiro") // Define o usuario
		.password("admin") // Define a senha do usuario
		.roles("ADMIN"); // Define o nivel de acesso ou perfil de usuario
	}
	
	@Override // Ignora URL especificas
	public void configure(WebSecurity web) throws Exception {
	
	}
}
