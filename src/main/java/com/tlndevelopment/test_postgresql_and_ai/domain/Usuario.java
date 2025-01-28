package com.tlndevelopment.test_postgresql_and_ai.domain;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Size(max = 60)
	private String nome;
	
	@NotNull(message = "Login não pode ser nulo")
	@Size(min = 3, max = 30, message = "o login deve ter entre 3 e 30 caracteres")
	private String login;
	
	@NotNull(message = "Senha não pode ser nula")
	@Size(min = 5, max = 80, message = "a senha deve ter entre 5 e 80 caracteres")
	private String senha;
	
	@NotNull(message = "Email não pode ser nulo")
	@Email(message = "Formato errado para email")
	@Size(min = 5, max = 100, message = "o email deve ter entre 5 e 100 caracteres")
	private String email;

	@ManyToMany
	@JoinTable(
			name = "usuario_filme",
			joinColumns = @JoinColumn(name = "usuario_id"),
			inverseJoinColumns = @JoinColumn(name = "filme_id")
	)
	private List<Filme> filmes = new ArrayList<>();
	
	public Usuario() {}
	
	public Usuario(Integer id, String login, String senha, String email, String nome) {
		this.id = id;
		this.login = login;
		this.senha = senha;
		this.email = email;
		this.nome = nome;
	}
	
	public Usuario(String login, String senha, String email, String nome) {
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

	public List<Filme> getFilmes() {
		return filmes;
	}

	public void setFilmes(List<Filme> filmes) {
		this.filmes = filmes;
	}

}
