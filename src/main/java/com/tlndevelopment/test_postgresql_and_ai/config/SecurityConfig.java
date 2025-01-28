package com.tlndevelopment.test_postgresql_and_ai.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tlndevelopment.test_postgresql_and_ai.service.CustomUserDetailsService;
import com.tlndevelopment.test_postgresql_and_ai.util.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

	@Autowired
	@Lazy
    private JwtAuthenticationFilter jwtAuthenticationFilter;
	
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/**", "/h2-console/**").permitAll() // Permite acesso público
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable());
        
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
	
	@Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, CustomUserDetailsService service) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(service)
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }
	
}
