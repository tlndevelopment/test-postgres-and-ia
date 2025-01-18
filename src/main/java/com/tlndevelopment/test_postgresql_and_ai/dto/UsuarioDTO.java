package com.tlndevelopment.test_postgresql_and_ai.dto;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDTO {

	private Integer id;
	
	private String nome;
	
	private String login;
	
	private String senha;
	
	private String email;
	
	private List<FilmeDTO> filmes = new ArrayList<>();;
	
	public UsuarioDTO() {}
	
	public UsuarioDTO(Integer id, String login, String senha, String email, String nome) {
		this.id = id;
		this.login = login;
		this.senha = senha;
		this.email = email;
		this.nome = nome;
	}
	
	public UsuarioDTO(String login, String senha, String email, String nome) {
		this.login = login;
		this.senha = senha;
		this.email = email;
		this.nome = nome;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<FilmeDTO> getFilmes() {
		return filmes;
	}

	public void setFilmes(List<FilmeDTO> filmes) {
		this.filmes = filmes;
	}
	
}
