package com.tlndevelopment.test_postgresql_and_ai.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "filme")
public class Filme {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "Título não pode ser nulo")
	@Size(min = 3, max = 100, message = "o título do filme deve ter entre 3 e 100 caracteres")
	private String titulo;
	
	@NotNull(message = "Ano de lançamento não pode ser nulo")
	private int anoLancamento;
	
	@ManyToMany(mappedBy = "filmes")
	private List<Usuario> usuarios;
	
	public Filme() {}
	
	public Filme (Integer id, String titulo, int anoLancamento) {
		this.id = id;
		this.titulo = titulo;
		this.anoLancamento = anoLancamento;
	}
	
	public Filme (String titulo, int anoLancamento) {
		this.titulo = titulo;
		this.anoLancamento = anoLancamento;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getAnoLancamento() {
		return anoLancamento;
	}

	public void setAnoLancamento(int anoLancamento) {
		this.anoLancamento = anoLancamento;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
}
