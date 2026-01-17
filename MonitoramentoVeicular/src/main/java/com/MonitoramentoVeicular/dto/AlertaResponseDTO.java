package com.MonitoramentoVeicular.dto;

import java.time.LocalDateTime;

import com.MonitoramentoVeicular.enum_.TipoAlerta;

public class AlertaResponseDTO {

	private Long id;
    private TipoAlerta tipo;
    private String descricao;
    private LocalDateTime dataHora;
    private Long veiculoId;
    private String placaVeiculo;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TipoAlerta getTipo() {
		return tipo;
	}
	public void setTipo(TipoAlerta tipo) {
		this.tipo = tipo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public LocalDateTime getDataHora() {
		return dataHora;
	}
	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}
	public Long getVeiculoId() {
		return veiculoId;
	}
	public void setVeiculoId(Long veiculoId) {
		this.veiculoId = veiculoId;
	}
	public String getPlacaVeiculo() {
		return placaVeiculo;
	}
	public void setPlacaVeiculo(String placaVeiculo) {
		this.placaVeiculo = placaVeiculo;
	}
    
    
}
