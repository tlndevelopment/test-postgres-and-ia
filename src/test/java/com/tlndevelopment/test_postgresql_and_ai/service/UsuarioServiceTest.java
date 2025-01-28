package com.tlndevelopment.test_postgresql_and_ai.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import com.tlndevelopment.test_postgresql_and_ai.domain.Usuario;
import com.tlndevelopment.test_postgresql_and_ai.dto.UsuarioDTO;
import com.tlndevelopment.test_postgresql_and_ai.repository.UsuarioRepository;

public class UsuarioServiceTest {
	
	@Mock
	private UsuarioRepository usuarioRepository;
	
	@InjectMocks
	private UsuarioService usuarioService;
	
	private Usuario usuario;
	
	private UsuarioDTO usuarioDTO;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		usuario = new Usuario(1, "Login", "senha123", "mail@mail.com", "");
		usuarioDTO = new UsuarioDTO();
	}
	
	@Test
	void deveCriarUsuario() {
		BeanUtils.copyProperties(usuario, usuarioDTO);
		
		when(usuarioRepository.save(usuario)).thenReturn(usuario);
		
		UsuarioDTO usuSalvoDTO = usuarioService.save(usuarioDTO); 
		
		assertNotNull(usuSalvoDTO);
		assertEquals(usuarioDTO.getLogin(), usuSalvoDTO.getLogin());
		assertEquals(usuarioDTO.getSenha(), usuSalvoDTO.getSenha());
		assertEquals(usuarioDTO.getEmail(), usuSalvoDTO.getEmail());
	}
	
	@Test
	public void deveEncontrarUsuario() {
		when(usuarioRepository.findById(1)).thenReturn(Optional.ofNullable(usuario));
		
		UsuarioDTO usuarioExistente = usuarioService.findById(1);
		
		assertNotNull(usuarioExistente);
		assertEquals(usuarioExistente.getLogin(), usuario.getLogin());
	}

}
