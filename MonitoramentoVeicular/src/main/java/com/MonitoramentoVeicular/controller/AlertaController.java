package com.MonitoramentoVeicular.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MonitoramentoVeicular.dto.AlertaResponseDTO;
import com.MonitoramentoVeicular.model.Alerta;
import com.MonitoramentoVeicular.service.AlertaService;

@RestController
@RequestMapping("/alertas")
public class AlertaController {

    private final AlertaService alertaService;

    public AlertaController(AlertaService alertaService) {
        this.alertaService = alertaService;
    }

    @GetMapping
    public ResponseEntity<List<AlertaResponseDTO>> listarTodos() {
        List<AlertaResponseDTO> lista = alertaService
                .listarTodos()
                .stream()
                .map(this::toDTO)
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/veiculos/{veiculoId}")
    public ResponseEntity<List<AlertaResponseDTO>> listarPorVeiculo(
            @PathVariable Long veiculoId) {

        List<AlertaResponseDTO> lista = alertaService
                .listarAlertasPorVeiculo(veiculoId)
                .stream()
                .map(this::toDTO)
                .toList();

        return ResponseEntity.ok(lista);
    }

    private AlertaResponseDTO toDTO(Alerta alerta) {
        AlertaResponseDTO dto = new AlertaResponseDTO();
        dto.setId(alerta.getId());
        dto.setTipo(alerta.getTipo());
        dto.setDescricao(alerta.getDescricao());
        dto.setDataHora(alerta.getDataHora());

        dto.setVeiculoId(alerta.getVeiculo().getId());
        dto.setPlacaVeiculo(alerta.getVeiculo().getPlaca());

        return dto;
    }
}