package org.serratec.h2.grupo2.security;

import org.serratec.h2.grupo2.exception.CustomAccessDeniedHandler;
import org.serratec.h2.grupo2.exception.CustomAuthenticationEntryPoint;
import org.serratec.h2.grupo2.security.tokenAcesso.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

	@Autowired
	private CustomAccessDeniedHandler customAccessDeniedHandler;

	@Autowired
	private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthFilter jwtAuthFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http
            .csrf(csrf -> csrf.disable())
			.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

		
			.exceptionHandling(exception -> exception
				    .accessDeniedHandler(customAccessDeniedHandler)
				    .authenticationEntryPoint(customAuthenticationEntryPoint)
				)
		
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login", "/auth/register").permitAll()

                //REQUISIÇÕES DE FUNCIONÁRIO
                //BAIXO NIVEL DE ACESSO
                .requestMatchers("/funcionario/atualizarCadastro").hasAnyRole("BAIXO", "MEDIO", "ALTO", "TOTAL")
                .requestMatchers("/funcionario/atualizacaoParcial").hasAnyRole("BAIXO", "MEDIO", "ALTO", "TOTAL")

                //MEDIO NIVEL DE ACESSO
                .requestMatchers("/funcionario/cadastro").permitAll()
                .requestMatchers("/funcionario/atualizarFuncionario").hasAnyRole("MEDIO", "ALTO", "TOTAL")
                .requestMatchers("/funcionario/atualizacaoParcialGestor/**").hasAnyRole("MEDIO", "ALTO", "TOTAL")
            	.requestMatchers("/funcionario/desativarConta/**").hasAnyRole("MEDIO", "ALTO", "TOTAL")
                .requestMatchers("/funcionario/ativarConta/**").hasAnyRole("MEDIO", "ALTO", "TOTAL")
                .requestMatchers("/funcionario/buscarPorId/**").hasAnyRole("MEDIO", "ALTO", "TOTAL")
                .requestMatchers("/funcionario/listarPorNome/**").hasAnyRole("MEDIO", "ALTO", "TOTAL")

                //ALTO NIVEL DE ACESSO
                .requestMatchers("/funcionario/listarFuncionarios").hasAnyRole("ALTO", "TOTAL")
                .requestMatchers("/funcionario/listarPeloCargo/**").hasAnyRole("ALTO", "TOTAL")
                .requestMatchers("/funcionario/listarContasAtivas").hasAnyRole("ALTO", "TOTAL")
                .requestMatchers("/funcionario/listarContasDesativadas").hasAnyRole("ALTO", "TOTAL")

                //NIVEL DE ACESSO TOTAL
                .requestMatchers("/funcionario/deletarFuncionario/**").hasAnyRole("TOTAL")


                .anyRequest().permitAll())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
	}
	
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	    return config.getAuthenticationManager();
	}

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
}
