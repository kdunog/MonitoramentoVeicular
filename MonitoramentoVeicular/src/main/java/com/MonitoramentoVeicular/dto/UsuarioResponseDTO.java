package com.MonitoramentoVeicular.dto;

import java.util.List;

import lombok.Data;

public class UsuarioResponseDTO {

	private Long id;
	private String nome;
	private String email;
	private List<VeiculoResponseDTO> veiculos;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<VeiculoResponseDTO> getVeiculos() {
		return veiculos;
	}
	public void setVeiculos(List<VeiculoResponseDTO> veiculos) {
		this.veiculos = veiculos;
	}
	
	
}
