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
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
    private final FilmeRepository filmeRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, FilmeRepository filmeRepository) {
        this.usuarioRepository = usuarioRepository;
        this.filmeRepository = filmeRepository;
    }
	
	private void validaAtributos(Usuario usuario) {
		validaLogin(usuario);
		validaEmail(usuario);
	}
	
	private void validaLogin(Usuario usuario) {
		Optional<Usuario> userFound = findByLogin(usuario.getLogin());
		if (userFound.isPresent()) {
			throw new ServiceException("Este login já está em uso");
		}
	}
	
	private void validaEmail(Usuario usuario) {
		Optional<Usuario> userFound = usuarioRepository.findByEmail(usuario.getEmail());
		if (userFound.isPresent()) {
			throw new ServiceException("Este email já está em uso");
		}
	}
	
	public Optional<Usuario> findByLogin(String login) {
		return usuarioRepository.findByLogin(login);
	}
	
	public UsuarioDTO save(UsuarioDTO dto) {
		String login = dto.getLogin();
		String senha = dto.getSenha();
		String email = dto.getEmail();
		String nome = dto.getNome();
		
		Usuario u = new Usuario(login, senha, email, nome);
		validaAtributos(u);
		
		usuarioRepository.saveAndFlush(u);
		
		dto.setId(u.getId());
		return dto;
	}
	
	public UsuarioDTO findById(Integer id) {
		Optional<Usuario> userFound = usuarioRepository.findById(id);
		if (userFound.isPresent()) {
			UsuarioDTO dto = new UsuarioDTO(userFound.get().getId(), userFound.get().getLogin(), userFound.get().getSenha(), userFound.get().getEmail(), userFound.get().getNome());
			dto.setFilmes(populaFilmes(userFound.get().getFilmes()));
			return dto;
		}
		throw new ServiceException("Usuário não encontrado");
	}
	
	public UsuarioDTO update(UsuarioDTO usuarioDTO, Integer id) {
		Optional<Usuario> userFound = usuarioRepository.findById(id);
		if (userFound.isPresent()) {
			usuarioDTO.setId(id);
			
			Usuario usuarioEditado = userFound.get();
			if(usuarioDTO.getLogin() != null) {
				usuarioEditado.setLogin(usuarioDTO.getLogin());
				validaLogin(usuarioEditado);
			} else
				usuarioDTO.setLogin(usuarioEditado.getLogin());
			if(usuarioDTO.getSenha() != null)
				usuarioEditado.setSenha(usuarioDTO.getSenha());
			else
				usuarioDTO.setSenha(usuarioEditado.getSenha());
			if(usuarioDTO.getEmail() != null) {
				usuarioEditado.setEmail(usuarioDTO.getEmail());
				validaEmail(usuarioEditado);
			} else
				usuarioDTO.setEmail(usuarioEditado.getEmail());
			if(usuarioDTO.getNome() != null)
				usuarioEditado.setNome(usuarioDTO.getNome());
			else
				usuarioDTO.setNome(usuarioEditado.getNome());
			
			usuarioRepository.saveAndFlush(usuarioEditado);
			
			usuarioDTO.setFilmes(populaFilmes(usuarioEditado.getFilmes()));
			return usuarioDTO;
		}
		throw new ServiceException("Usuário não encontrado");
		
	}
	
	public void delete(Integer id) {
		this.usuarioRepository.deleteById(id);
	}
	
	public void deleteAll() {
		this.usuarioRepository.deleteAll();
	}
	
	public List<UsuarioDTO> findAll() {
		List<UsuarioDTO> userReturn = new ArrayList<>();
		List<Usuario> usuarios = usuarioRepository.findAll();
		
		for(Usuario usuario : usuarios) {
			UsuarioDTO usuarioDTO = new UsuarioDTO(usuario.getId(), usuario.getLogin(), usuario.getSenha(), usuario.getEmail(), usuario.getNome());
			usuarioDTO.setFilmes(populaFilmes(usuario.getFilmes()));
			userReturn.add(usuarioDTO);
		}
		return userReturn;
	}
	
	private List<FilmeDTO> populaFilmes(List<Filme> filmes) {
		List<FilmeDTO> dto = new ArrayList<>();
		
		for (Filme filme : filmes) {
			dto.add(new FilmeDTO(filme.getId(), filme.getTitulo(), filme.getAnoLancamento()));
		}
		
		return dto;
	}
	
	public UsuarioDTO addFilme(Integer idUsuario, Integer idFilme) {
		Optional<Filme> movieInUser = filmeRepository.getMovieInUser(idUsuario, idFilme);
		if(movieInUser.isPresent()) {
			throw new ServiceException("Usuário já possui este filme no catálogo");
		}
		
		Optional<Usuario> userFound = usuarioRepository.findById(idUsuario);
		Optional<Filme> movieFound = filmeRepository.findById(idFilme);
		if(!userFound.isPresent() || !movieFound.isPresent())
			throw new ServiceException("Filme ou Usuário não encontrados");
		
		Usuario u = userFound.get();
		List<Filme> filmes = u.getFilmes();
		filmes.add(movieFound.get());
		u.setFilmes(filmes);
		usuarioRepository.saveAndFlush(u);

		UsuarioDTO dto = new UsuarioDTO(u.getId(), u.getLogin(), u.getSenha(), u.getEmail(), u.getNome()); 
		dto.setFilmes(populaFilmes(filmes));
		
		return dto;
	}
	
}
