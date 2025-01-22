package com.tlndevelopment.test_postgresql_and_ai.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tlndevelopment.test_postgresql_and_ai.domain.Usuario;
import com.tlndevelopment.test_postgresql_and_ai.service.UsuarioService;
import com.tlndevelopment.test_postgresql_and_ai.util.JwtUtil;

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
	
//	@PostMapping("/login")
//	public ResponseEntity<?> login (@RequestBody Usuario usuario) {
//		Optional<Usuario> foundUser = usuarioService.findByLogin(usuario.getLogin());
//		if (foundUser.isPresent() && foundUser.get().getSenha().equals(usuario.getSenha())) {
//			String token = jwtUtil.generateToken(usuario.getLogin());
//			return ResponseEntity.ok().body("{\"accesToken\": \"" + token + "\"}");
//		}
//		
//		return ResponseEntity.status(401).body("Credenciais Inválidas");
//	}
	
	@PostMapping("/login")
    public String login(@RequestBody Usuario usuario){
        try {
        	Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha()));
                    return jwtUtil.generateToken(authentication.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Autenticação falhou", e);
		}
	}
	
}
