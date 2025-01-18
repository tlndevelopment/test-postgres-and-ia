package com.tlndevelopment.test_postgresql_and_ai.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlndevelopment.test_postgresql_and_ai.domain.Filme;
import com.tlndevelopment.test_postgresql_and_ai.domain.Usuario;
import com.tlndevelopment.test_postgresql_and_ai.dto.FilmeDTO;
import com.tlndevelopment.test_postgresql_and_ai.dto.UsuarioDTO;
import com.tlndevelopment.test_postgresql_and_ai.repository.FilmeRepository;
import com.tlndevelopment.test_postgresql_and_ai.repository.UsuarioRepository;

@Service
public class FilmeService {

	private final FilmeRepository filmeRepository;
	private final  UsuarioRepository usuarioRepository;
	
	@Autowired
	public FilmeService(FilmeRepository filmeRepository, UsuarioRepository usuarioRepository) {
		this.filmeRepository = filmeRepository;
		this.usuarioRepository = usuarioRepository;
	}
	
	private void validaTitulo(Filme filme) {
		Optional<Filme> filmeExistente = filmeRepository.findByTitle(filme.getTitulo());
		if(filmeExistente.isPresent()) 
			throw new ServiceException("Filme já cadastrado");
	}
	
	public FilmeDTO save(FilmeDTO dto) {
		String titulo = dto.getTitulo();
		int anoLancamento = dto.getAnoLancamento();
		
		Filme f = new Filme(titulo, anoLancamento);
		validaTitulo(f);
		
		filmeRepository.saveAndFlush(f);
		
		dto.setId(f.getId());
		return dto;
	}
	
	public FilmeDTO findById(Integer id) {
		Optional<Filme> filme = filmeRepository.findById(id);
		if(filme.isPresent()) {
			FilmeDTO dto = new FilmeDTO(filme.get().getId(), filme.get().getTitulo(), filme.get().getAnoLancamento());
			dto.setUsuarios(populaUsuarios(filme.get().getUsuarios()));
			return dto;
		}
		throw new ServiceException("Filme não encontrado");
	}
	
	public FilmeDTO update(FilmeDTO dto, Integer id) {
		Optional<Filme> filme = filmeRepository.findById(id);
		if(filme.isPresent()) {
			dto.setId(id);
			
			Filme filmeEditado = filme.get();
			if(dto.getTitulo() != null) {
				filmeEditado.setTitulo(dto.getTitulo());
				validaTitulo(filmeEditado);
			} else
				dto.setTitulo(null);
			if(dto.getAnoLancamento() != 0)
				filmeEditado.setAnoLancamento(dto.getAnoLancamento());
			else
				dto.setAnoLancamento(filmeEditado.getAnoLancamento());

			validaTitulo(filmeEditado);
			filmeRepository.saveAndFlush(filmeEditado);
			
			return dto;
		}
		throw new ServiceException("Filme não encontrado");
	}
	
	public void delete(Integer id) {
		filmeRepository.deleteById(id);
	}
	
	public void deleteAll() {
		filmeRepository.deleteAll();
	}
	
	public List<FilmeDTO> findAll() {
		List<FilmeDTO> filmesDTO = new ArrayList<>();
		List<Filme> filmes = filmeRepository.findAll();
		
		for(Filme f : filmes) {
			FilmeDTO dto = new FilmeDTO(f.getId(), f.getTitulo(), f.getAnoLancamento());
			dto.setUsuarios(populaUsuarios(f.getUsuarios()));
			filmesDTO.add(dto);
		}
		
		return filmesDTO;
	}
	
	private List<UsuarioDTO> populaUsuarios(List<Usuario> usuarios) {
		List<UsuarioDTO> dto = new ArrayList<>();
		
		for(Usuario u : usuarios) {
			dto.add(new UsuarioDTO(u.getId(), u.getLogin(), u.getSenha(), u.getEmail(), u.getNome()));
		}
		
		return dto;
	}
	
	public FilmeDTO addUsuario(Integer idFilme, Integer idUsuario) {
		Optional<Usuario> userInMovie = usuarioRepository.getUserInMovie(idUsuario, idFilme);
		if(userInMovie.isPresent())
			throw new ServiceException("Usuário já possui este filme no catálogo");
		
		Optional<Usuario> userFound = usuarioRepository.findById(idUsuario);
		Optional<Filme> movieFound = filmeRepository.findById(idFilme);
		if(!userFound.isPresent() || !movieFound.isPresent())
			throw new ServiceException("Filme ou Usuário não encontrados");
		
		Filme f = movieFound.get();
		Usuario u = userFound.get();
		List<Usuario> usuarios = f.getUsuarios();
		usuarios.add(u);
		f.setUsuarios(usuarios);
		filmeRepository.saveAndFlush(f);
		
		List<Filme> filmes = u.getFilmes(); 
		filmes.add(f);
		u.setFilmes(filmes);
		usuarioRepository.saveAndFlush(u);
		
		
		FilmeDTO dto = new FilmeDTO(f.getId(), f.getTitulo(), f.getAnoLancamento());
		dto.setUsuarios(populaUsuarios(usuarios));
		
		return dto;
	}
}
