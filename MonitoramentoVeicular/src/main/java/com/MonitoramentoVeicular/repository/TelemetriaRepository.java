package com.MonitoramentoVeicular.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.MonitoramentoVeicular.model.Telemetria;
import com.MonitoramentoVeicular.model.Veiculo;

public interface TelemetriaRepository extends JpaRepository <Telemetria, Long> {
	
	List<Telemetria> findByVeiculoIdOrderByDataHoraDesc(Long veiculoId);

    Optional <Telemetria> findTopByVeiculoIdOrderByDataHoraDesc(Long veiculoId);
    
    Optional<Telemetria> findTopByVeiculoOrderByDataHoraDesc(Veiculo veiculo);

    
    void deleteByVeiculoId(Long veiculoId);

}
