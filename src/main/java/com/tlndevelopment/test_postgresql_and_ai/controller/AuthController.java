package com.tlndevelopment.test_postgresql_and_ai.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tlndevelopment.test_postgresql_and_ai.domain.Usuario;
import com.tlndevelopment.test_postgresql_and_ai.security.JwtUtil;
import com.tlndevelopment.test_postgresql_and_ai.service.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UsuarioService usuarioService;
	
	private final JwtUtil jwtUtil;
	
	public AuthController(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login (@RequestBody Usuario usuario) {
		Optional<Usuario> foundUser = usuarioService.findByLogin(usuario.getLogin());
		if (foundUser.isPresent() && foundUser.get().getSenha().equals(usuario.getSenha())) {
			String token = jwtUtil.generateToken(usuario.getLogin());
			return ResponseEntity.ok().body("{\"accesToken\": \"" + token + "\"}");
		}
		
		return ResponseEntity.status(401).body("Credenciais Inválidas");
	}
	
}
