package com.MonitoramentoVeicular.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MonitoramentoVeicular.dto.LoginRequestDTO;
import com.MonitoramentoVeicular.dto.UsuarioRequestDTO;
import com.MonitoramentoVeicular.dto.UsuarioResponseDTO;
import com.MonitoramentoVeicular.model.Usuario;
import com.MonitoramentoVeicular.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(
          @Valid  @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.criarUsuario(dto));
    }

    // READ - LISTAR TODOS
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    // READ - POR ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obterUsuarioPorId(
            @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obterUsuarioPorId(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(
            @PathVariable Long id,
            @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(id, dto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
    
    //LOGIN
    @PostMapping("/login")
	public ResponseEntity<UsuarioResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
		return ResponseEntity.ok(usuarioService.login(dto));
	}
}