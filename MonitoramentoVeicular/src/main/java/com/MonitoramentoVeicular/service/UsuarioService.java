package com.MonitoramentoVeicular.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.MonitoramentoVeicular.dto.LoginRequestDTO;
import com.MonitoramentoVeicular.dto.UsuarioRequestDTO;
import com.MonitoramentoVeicular.dto.UsuarioResponseDTO;
import com.MonitoramentoVeicular.model.Usuario;
import com.MonitoramentoVeicular.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // CREATE
    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Usuário com este email já existe.");
        }

        Usuario usuario = new Usuario();
        usuario.setName(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setRole("USER");

        Usuario salvo = usuarioRepository.save(usuario);
        return toResponseDTO(salvo);
    }

    // READ - LISTAR
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // READ - POR ID
    public UsuarioResponseDTO obterUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Usuário não encontrado com o ID: " + id));

        return toResponseDTO(usuario);
    }

    // UPDATE
    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO dto) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Usuário não encontrado com o ID: " + id));

        usuarioExistente.setName(dto.getNome());
        usuarioExistente.setEmail(dto.getEmail());

        Usuario atualizado = usuarioRepository.save(usuarioExistente);
        return toResponseDTO(atualizado);
    }

    // DELETE
    public void deletarUsuario(Long id) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Usuário não encontrado com o ID: " + id));

        usuarioRepository.delete(usuarioExistente);
    }

    // ==========================
    // CONVERSÃO ENTITY -> DTO
    // ==========================
    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getName());
        dto.setEmail(usuario.getEmail());

        if (usuario.getVeiculos() != null) {
            dto.setVeiculos(
                usuario.getVeiculos().stream().map(v -> {
                    var veiculoDTO = new com.MonitoramentoVeicular.dto.VeiculoResponseDTO();
                    veiculoDTO.setId(v.getId());
                    veiculoDTO.setModelo(v.getModelo());
                    veiculoDTO.setPlaca(v.getPlaca());
                    veiculoDTO.setAno(v.getAno());
                    return veiculoDTO;
                }).toList()
            );
        }

        return dto;
    }
    
	public UsuarioResponseDTO login(LoginRequestDTO dto) {
		Usuario usuario = usuarioRepository.findByEmail(dto.getEmail()).orElseThrow(
				() -> new IllegalArgumentException("Usuário não encontrado com o email: " + dto.getEmail()));

		if (!usuario.getSenha().equals(dto.getSenha())) {
			throw new IllegalArgumentException("Senha incorreta.");
		}

		return toResponseDTO(usuario);
	}
}
