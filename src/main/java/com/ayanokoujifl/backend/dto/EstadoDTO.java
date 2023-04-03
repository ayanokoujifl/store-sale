package com.ayanokoujifl.backend.dto;

import com.ayanokoujifl.backend.entities.Estado;

public class EstadoDTO {

	private Integer id;
	private String nome;
	
	public EstadoDTO() {
	}
	
	public EstadoDTO(Estado obj) {
		this.id=obj.getId();
		this.nome=obj.getNome();
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
}
