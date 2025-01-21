package com.tlndevelopment.test_postgresql_and_ai.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/register", "/auth/login", "/auth/refresh", "/h2-console/**", "/api/**").permitAll() // Permite acesso público
                        .anyRequest().authenticated()
                );

        return http.build();
    }
	
}
