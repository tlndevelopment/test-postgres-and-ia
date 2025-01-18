package com.tlndevelopment.test_postgresql_and_ai.dto;

import java.util.ArrayList;
import java.util.List;

public class FilmeDTO {

	private Integer id;
	
	private String titulo;
	
	private int anoLancamento;
	
	private List<UsuarioDTO> usuarios = new ArrayList<>();;
	
	public FilmeDTO() {}
	
	public FilmeDTO (Integer id, String titulo, int anoLancamento) {
		this.id = id;
		this.titulo = titulo;
		this.anoLancamento = anoLancamento;
	}
	
	public FilmeDTO (String titulo, int anoLancamento) {
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

	public List<UsuarioDTO> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioDTO> usuarios) {
		this.usuarios = usuarios;
	}
	
}
