package com.MonitoramentoVeicular.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MonitoramentoVeicular.model.Telemetria;
import com.MonitoramentoVeicular.service.TelemetriaService;

@RestController
@RequestMapping("/telemetrias")
public class TelemetriaController {
	
	private final TelemetriaService telemetriaService;
	
	public TelemetriaController(TelemetriaService telemetriaService) {
		this.telemetriaService = telemetriaService;
	}
	
	@PostMapping
    public ResponseEntity<Telemetria> criarTelemetria(
            @RequestBody Telemetria telemetria) {

        Telemetria salva = telemetriaService.salvarTelemetria(telemetria);
        return ResponseEntity.ok(salva);
    }
	
	@GetMapping("/veiculos/{veiculoId}")
	public List<Telemetria> listarPorVeiculo(@PathVariable Long veiculoId) {
		return telemetriaService.listarPorVeiculo(veiculoId);
	}
	
	
	@DeleteMapping("/veiculos/{veiculoId}")
	public ResponseEntity<Void> deletarTelemetriasPorVeiculo(@PathVariable Long veiculoId) {
		telemetriaService.deletarTelemetriasPorVeiculo(veiculoId);
		
		return ResponseEntity.noContent().build();
	}

}
