package com.tlndevelopment.test_postgresql_and_ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tlndevelopment.test_postgresql_and_ai.dto.UsuarioDTO;
import com.tlndevelopment.test_postgresql_and_ai.service.CustomUserDetailsService;
import com.tlndevelopment.test_postgresql_and_ai.service.UsuarioService;
import com.tlndevelopment.test_postgresql_and_ai.util.JwtUtil;

import jakarta.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
    private AuthenticationManager authenticationManager;

	@Autowired
    private JwtUtil jwtUtil;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@PostMapping(value = "/cadastrar")
	public ResponseEntity<UsuarioDTO> createUser(@RequestBody @Valid UsuarioDTO usuarioDTO) {
		UsuarioDTO usuario = usuarioService.save(usuarioDTO);
		
		return new ResponseEntity<UsuarioDTO>(usuario, HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
    public String login(@RequestBody UsuarioDTO usuario){
        try {
        	Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha()));
                    return jwtUtil.generateToken(authentication.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Autenticação falhou", e);
		}
	}
	
    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@RequestBody String refreshToken) {
        try {
            String username = jwtUtil.getUsernameFromToken(refreshToken);

            if(jwtUtil.isTokenValid(refreshToken, customUserDetailsService.loadUserByUsername(username))) {
                String newAccessToken = jwtUtil.generateToken(username);
                return ResponseEntity.ok(newAccessToken);
            } else {
                return ResponseEntity.status(401).body("Refresh Token Inválido!");
            }
        } catch(Exception error){
            return ResponseEntity.status(401).body("Erro ao processar o Refresh Token!");
        }
    }

}
