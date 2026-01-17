package com.MonitoramentoVeicular.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.MonitoramentoVeicular.dto.VeiculoRequestDTO;
import com.MonitoramentoVeicular.dto.VeiculoResponseDTO;
import com.MonitoramentoVeicular.model.Usuario;
import com.MonitoramentoVeicular.model.Veiculo;
import com.MonitoramentoVeicular.repository.TelemetriaRepository;
import com.MonitoramentoVeicular.repository.UsuarioRepository;
import com.MonitoramentoVeicular.repository.VeiculoRepository;

import jakarta.transaction.Transactional;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final UsuarioRepository usuarioRepository;
    private final TelemetriaRepository telemetriaRepository;

    public VeiculoService(VeiculoRepository veiculoRepository,
                          UsuarioRepository usuarioRepository,
                          TelemetriaRepository telemetriaRepository) {
        this.veiculoRepository = veiculoRepository;
        this.usuarioRepository = usuarioRepository;
        this.telemetriaRepository = telemetriaRepository;
    }

    // CREATE
    public VeiculoResponseDTO cadastrarVeiculo(VeiculoRequestDTO dto, Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Usuário não encontrado com o ID: " + usuarioId));

        Veiculo veiculo = new Veiculo();
        veiculo.setModelo(dto.getModelo());
        veiculo.setPlaca(dto.getPlaca());
        veiculo.setAno(dto.getAno());
        veiculo.setAtivo(true);
        veiculo.setUsuario(usuario);

        Veiculo salvo = veiculoRepository.save(veiculo);
        return toResponseDTO(salvo);
    }

    // READ - LISTAR TODOS
    public List<VeiculoResponseDTO> listarVeiculos() {
        return veiculoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // READ - LISTAR POR USUÁRIO
    public List<VeiculoResponseDTO> listarVeiculosPorUsuario(Long usuarioId) {
        return veiculoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // READ - POR ID
    public VeiculoResponseDTO obterVeiculoPorId(Long id) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Veículo não encontrado com o ID: " + id));

        return toResponseDTO(veiculo);
    }

    // UPDATE
    public VeiculoResponseDTO atualizarVeiculo(Long id, VeiculoRequestDTO dto) {
        Veiculo veiculoExistente = veiculoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Veículo não encontrado com o ID: " + id));

        veiculoExistente.setModelo(dto.getModelo());
        veiculoExistente.setAno(dto.getAno());
        veiculoRepository.save(veiculoExistente);

        return toResponseDTO(veiculoExistente);
    }

    // DELETE
    @Transactional
    public void deletarVeiculo(Long id) {

        Veiculo veiculoExistente = veiculoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Veículo não encontrado com o ID: " + id));

        // 1️ Deleta as telemetrias vinculadas ao veículo
        telemetriaRepository.deleteByVeiculoId(id);

        // 2️ Agora o banco permite deletar o veículo
        veiculoRepository.delete(veiculoExistente);
    }

    // DESATIVAR (soft delete)
    public void desativarVeiculo(Long id) {
        Veiculo veiculoExistente = veiculoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Veículo não encontrado com o ID: " + id));

        veiculoExistente.setAtivo(false);
        veiculoRepository.save(veiculoExistente);
    }

    // ==========================
    // CONVERSÃO ENTITY -> DTO
    // ==========================
    private VeiculoResponseDTO toResponseDTO(Veiculo v) {
        VeiculoResponseDTO dto = new VeiculoResponseDTO();

        dto.setId(v.getId());
        dto.setModelo(v.getModelo());
        dto.setPlaca(v.getPlaca());
        dto.setAno(v.getAno());

        return dto;
    }
}
