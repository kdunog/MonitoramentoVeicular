package com.MonitoramentoVeicular.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.MonitoramentoVeicular.enum_.TipoAlerta;
import com.MonitoramentoVeicular.model.Alerta;
public interface AlertaRepository extends JpaRepository <Alerta, Long> {
	
	List<Alerta> findByVeiculoId(Long veiculoId);

    List<Alerta> findByTipo(String tipo);
    
    boolean existsByVeiculoIdAndTipoAndDataHoraAfter(Long veiculoId, TipoAlerta tipo, LocalDateTime dataHora);

}
