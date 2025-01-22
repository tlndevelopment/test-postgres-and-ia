package com.tlndevelopment.test_postgresql_and_ai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tlndevelopment.test_postgresql_and_ai.dto.UsuarioDTO;
import com.tlndevelopment.test_postgresql_and_ai.service.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "api")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping(value = "/usuarios")
	public ResponseEntity<List<UsuarioDTO>> getUsers() {
		List<UsuarioDTO> usuarios = usuarioService.findAll();
		
		return new ResponseEntity<List<UsuarioDTO>>(usuarios, HttpStatus.OK);
	}
	
	@GetMapping(value = "/usuario/{id}")
	public ResponseEntity<UsuarioDTO> getUser(@PathVariable Integer id) {
		UsuarioDTO usuario = usuarioService.findById(id);
		
		return new ResponseEntity<UsuarioDTO>(usuario, HttpStatus.OK);
	}
	
	@PostMapping(value = "/usuario")
	public ResponseEntity<UsuarioDTO> createUser(@RequestBody @Valid UsuarioDTO usuarioDTO) {
		UsuarioDTO usuario = usuarioService.save(usuarioDTO);
		
		return new ResponseEntity<UsuarioDTO>(usuario, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/usuario/{id}")
	public ResponseEntity<UsuarioDTO> updateUser(@RequestBody @Valid UsuarioDTO usuarioDTO, @PathVariable Integer id) {
		UsuarioDTO usuario = usuarioService.update(usuarioDTO, id);
		
		return new ResponseEntity<UsuarioDTO>(usuario, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/usuario/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
		usuarioService.delete(id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/usuario/{idUsuario}/add/filme/{idFilme}")
	public ResponseEntity<UsuarioDTO> addMovieInUser(@PathVariable Integer idUsuario, @PathVariable Integer idFilme) {
		UsuarioDTO usuario = usuarioService.addFilme(idUsuario, idFilme);
		
		return new ResponseEntity<UsuarioDTO>(usuario, HttpStatus.OK);
	}
}
