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

import com.tlndevelopment.test_postgresql_and_ai.dto.FilmeDTO;
import com.tlndevelopment.test_postgresql_and_ai.service.FilmeService;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "api")
public class FilmeController {

	@Autowired
	private FilmeService filmeService;
	
	@GetMapping(value = "/filmes")
	public ResponseEntity<List<FilmeDTO>> getMovies() {
		List<FilmeDTO> filmes = filmeService.findAll();
		
		return new ResponseEntity<List<FilmeDTO>>(filmes, HttpStatus.OK);
	}
	
	@GetMapping(value = "/filme/{id}")
	public ResponseEntity<FilmeDTO> getMovie(@PathVariable Integer id) {
		FilmeDTO filme = filmeService.findById(id);
		
		return new ResponseEntity<FilmeDTO>(filme, HttpStatus.OK);
	}
	
	@PostMapping(value = "/filme")
	public ResponseEntity<FilmeDTO> createMovie(@RequestBody @Valid FilmeDTO dto) {
		FilmeDTO filme = filmeService.save(dto);
		
		return new ResponseEntity<FilmeDTO>(filme, HttpStatus.OK);
	}
	
	@PutMapping(value = "/filme/{id}")
	public ResponseEntity<FilmeDTO> updateMovie(@RequestBody @Valid FilmeDTO dto, @PathVariable Integer id) {
		FilmeDTO filme = filmeService.update(dto, id);
		
		return new ResponseEntity<FilmeDTO>(filme, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/filme/{id}")
	public ResponseEntity<?> deleteMovie(@PathVariable Integer id) {
		filmeService.delete(id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/filme/{idFilme}/add/usuario/{idUsuario}")
	public ResponseEntity<FilmeDTO> addUserInMovie(@PathVariable Integer idFilme, @PathVariable Integer idUsuario) {
		FilmeDTO filme = filmeService.addUsuario(idFilme, idUsuario);
		
		return new ResponseEntity<FilmeDTO>(filme, HttpStatus.OK);
	}
}
