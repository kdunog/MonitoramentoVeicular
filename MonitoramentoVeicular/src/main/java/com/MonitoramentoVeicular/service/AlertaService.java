package com.MonitoramentoVeicular.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.MonitoramentoVeicular.enum_.TipoAlerta;
import com.MonitoramentoVeicular.model.Alerta;
import com.MonitoramentoVeicular.model.Telemetria;
import com.MonitoramentoVeicular.model.Veiculo;
import com.MonitoramentoVeicular.repository.AlertaRepository;
import com.MonitoramentoVeicular.repository.VeiculoRepository;

@Service
public class AlertaService {

    private static final int COOLDOWN_MINUTOS = 10;
    private final AlertaRepository alertaRepository;

    public AlertaService(AlertaRepository alertaRepository) {
        this.alertaRepository = alertaRepository;
    }

    public void verificarAlertas(Telemetria t) {

        verificarComCooldown(
                t.getVeiculo(),
                TipoAlerta.VELOCIDADE_ALTA,
                t.getVelocidade() > 120,
                "Velocidade acima de 120 km/h"
        );
        
        verificarComCooldown(
                t.getVeiculo(),
                TipoAlerta.VELOCIADADE_MODERADA,
                t.getVelocidade() > 60 && t.getVelocidade() <= 120,
                "Velocidade entre 61 e 120 km/h"
        );
        
        verificarComCooldown(
                t.getVeiculo(),
                TipoAlerta.VELOCIDADE_BAIXA,
                t.getVelocidade() > 0 && t.getVelocidade() <= 60,
                "Velocidade até 60 km/h"
        );

        verificarComCooldown(
                t.getVeiculo(),
                TipoAlerta.CONSUMO_ALTO,
                t.getConsumo() < 7,
                "Consumo elevado detectado"
        );
        
        verificarComCooldown(
                t.getVeiculo(),
                TipoAlerta.CONSUMO_MODERADO,
                t.getConsumo() > 7 && t.getConsumo() <= 12,
                "Consumo moderado detectado"
        );
        
        verificarComCooldown(
                t.getVeiculo(),
                TipoAlerta.CONSUMO_BAIXO,
                t.getConsumo() > 12 && t.getConsumo() <= 18,
                "Consumo baixo detectado"
        );

        verificarComCooldown(
                t.getVeiculo(),
                TipoAlerta.COMBUSTIVEL_BAIXO,
                t.getCombustivel() < 15,
                "Combustível abaixo de 15%"
        );
        
        verificarComCooldown(
                t.getVeiculo(),
                TipoAlerta.COMBUSTIVEL_MODERADO,
                t.getCombustivel() > 15 && t.getCombustivel() <= 50,
                "Combustível entre 15% a 50%"
        );
        
        verificarComCooldown(
                t.getVeiculo(),
                TipoAlerta.COMBUSTIVEL_ALTO,
                t.getCombustivel() > 50 && t.getCombustivel() <= 100,
                "Combustível acima 50%"
        );
    }

    private void verificarComCooldown(
            Veiculo veiculo,
            TipoAlerta tipo,
            boolean condicao,
            String descricao
    ) {
        if (!condicao) return;

        LocalDateTime limite = LocalDateTime.now()
                .minusMinutes(COOLDOWN_MINUTOS);

        boolean existe = alertaRepository
                .existsByVeiculoIdAndTipoAndDataHoraAfter(
                        veiculo.getId(),
                        tipo,
                        limite
                );

        if (existe) return;

        Alerta alerta = new Alerta();
        alerta.setTipo(tipo);
        alerta.setDescricao(descricao);
        alerta.setVeiculo(veiculo);
        alerta.setDataHora(LocalDateTime.now());

        alertaRepository.save(alerta);
    }

    public List<Alerta> listarAlertasPorVeiculo(Long veiculoId) {
        return alertaRepository.findByVeiculoId(veiculoId);
    }
    
    public List<Alerta> listarTodos() {
        return alertaRepository.findAll();
    }

}