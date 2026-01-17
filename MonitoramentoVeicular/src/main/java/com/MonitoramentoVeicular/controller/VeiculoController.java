package com.MonitoramentoVeicular.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.MonitoramentoVeicular.dto.VeiculoRequestDTO;
import com.MonitoramentoVeicular.dto.VeiculoResponseDTO;
import com.MonitoramentoVeicular.model.Veiculo;
import com.MonitoramentoVeicular.service.VeiculoService;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    // CREATE
    @PostMapping("/usuarios/{usuarioId}")
    public ResponseEntity<VeiculoResponseDTO> cadastrarVeiculo(
            @PathVariable Long usuarioId,
            @RequestBody VeiculoRequestDTO dto) {
        return ResponseEntity.ok(
                veiculoService.cadastrarVeiculo(dto, usuarioId));
    }

    // READ - LISTAR TODOS
    @GetMapping
    public ResponseEntity<List<VeiculoResponseDTO>> listarVeiculos() {
        return ResponseEntity.ok(veiculoService.listarVeiculos());
    }

    // READ - LISTAR POR USUÁRIO
    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<List<VeiculoResponseDTO>> listarVeiculosPorUsuario(
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(
                veiculoService.listarVeiculosPorUsuario(usuarioId));
    }

    // READ - POR ID
    @GetMapping("/{id}")
    public ResponseEntity<VeiculoResponseDTO> obterVeiculoPorId(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                veiculoService.obterVeiculoPorId(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<VeiculoResponseDTO> atualizarVeiculo(
            @PathVariable Long id,
            @RequestBody VeiculoRequestDTO dto) {
        return ResponseEntity.ok(
                veiculoService.atualizarVeiculo(id, dto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVeiculo(@PathVariable Long id) {
        veiculoService.deletarVeiculo(id);
        return ResponseEntity.noContent().build();
    }

    // DESATIVAR (SOFT DELETE)
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativarVeiculo(@PathVariable Long id) {
        veiculoService.desativarVeiculo(id);
        return ResponseEntity.noContent().build();
    }
}