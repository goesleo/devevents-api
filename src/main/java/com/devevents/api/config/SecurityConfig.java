package com.devevents.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable()).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(authorize -> authorize
                        // Rotas de Autenticação são públicas
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll().requestMatchers(HttpMethod.POST, "/auth/register").permitAll()

                        // Apenas ADMIN pode criar eventos
                        .requestMatchers(HttpMethod.POST, "/events").hasRole("ADMIN")

                        // Qualquer usuário autenticado pode se inscrever ou ver eventos
                        .anyRequest().authenticated())
                // Adiciona o nosso filtro antes do filtro padrão do Spring
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // O padrão ouro para guardar senhas com segurança
    }
}