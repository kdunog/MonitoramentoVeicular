package com.MonitoramentoVeicular.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MonitoramentoVeicular.model.Veiculo;

public interface VeiculoRepository extends JpaRepository <Veiculo, Long>{

	Optional <Veiculo> findByPlaca(String placa);
	
	List<Veiculo> findByAtivoTrue();
	List<Veiculo> findByUsuarioId(Long usuarioId);
}
