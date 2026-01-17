package com.MonitoramentoVeicular.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.MonitoramentoVeicular.model.Telemetria;
import com.MonitoramentoVeicular.model.Veiculo;
import com.MonitoramentoVeicular.repository.TelemetriaRepository;
import com.MonitoramentoVeicular.repository.VeiculoRepository;

import jakarta.transaction.Transactional;

@Service
public class TelemetriaService {
	
	private final TelemetriaRepository telemetriaRepository;
	private final VeiculoRepository veiculoRepository;
	private final AlertaService alertaService;
	
	public TelemetriaService(TelemetriaRepository telemetriaRepository, VeiculoRepository veiculoRepository, AlertaService alertaService) {
		this.telemetriaRepository = telemetriaRepository;
		this.veiculoRepository = veiculoRepository;
		this.alertaService = alertaService;
	}
	
	public Telemetria salvarTelemetria(Telemetria telemetria) {

	    Veiculo veiculo = veiculoRepository.findById(
	            telemetria.getVeiculo().getId()
	    ).orElseThrow(() ->
	            new IllegalArgumentException("Veículo não encontrado")
	    );

	    telemetria.setVeiculo(veiculo);
	    telemetria.setDataHora(LocalDateTime.now());

	    Telemetria salva = telemetriaRepository.save(telemetria);

	    alertaService.verificarAlertas(salva);

	    return salva;
	}

	
	public List<Telemetria> listarPorVeiculo(Long veiculoId) {
		return telemetriaRepository.findByVeiculoIdOrderByDataHoraDesc(veiculoId);
	}

	public Optional<Telemetria> buscaUltimaTelemetriaPorVeiculo(Long veiculoId) {
		return telemetriaRepository.findTopByVeiculoIdOrderByDataHoraDesc(veiculoId);
	}
	
	@Transactional
	public void deletarTelemetriasPorVeiculo(Long veiculoId) {
		telemetriaRepository.deleteByVeiculoId(veiculoId);
	}
}
