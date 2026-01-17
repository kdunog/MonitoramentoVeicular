package com.MonitoramentoVeicular.iot;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.MonitoramentoVeicular.model.Telemetria;
import com.MonitoramentoVeicular.model.Veiculo;
import com.MonitoramentoVeicular.repository.TelemetriaRepository;
import com.MonitoramentoVeicular.repository.VeiculoRepository;
import com.MonitoramentoVeicular.service.AlertaService;
import com.MonitoramentoVeicular.service.TelemetriaService;

@Component
public class TelemetriaScheduler {

    private final TelemetriaRepository telemetriaRepository;
    private final VeiculoRepository veiculoRepository;
    private final AlertaService alertaService;
    private final Random random = new Random();

    public TelemetriaScheduler(
            TelemetriaRepository telemetriaRepository,
            VeiculoRepository veiculoRepository,
            AlertaService alertaService) {
        this.telemetriaRepository = telemetriaRepository;
        this.veiculoRepository = veiculoRepository;
        this.alertaService = alertaService;
    }

    /**
     * Gera telemetria a cada 10 segundos
     */
    @Scheduled(fixedRate = 10000)
    public void gerarTelemetria() {

        List<Veiculo> veiculos = veiculoRepository.findAll();

        for (Veiculo v : veiculos) {

            Telemetria t = new Telemetria();
            t.setVeiculo(v);
            t.setDataHora(LocalDateTime.now());

            /* ===========================
             * 1. Latitude / Longitude (Brasil)
             * =========================== */

            double lat = -33.75 + random.nextDouble() * (5.27 + 33.75);
            double lon = -73.99 + random.nextDouble() * (-34.79 + 73.99);

            lat = clamp(lat, -33.75, 5.27);
            lon = clamp(lon, -73.99, -34.79);

            t.setLatitude(round(lat, 5));
            t.setLongitude(round(lon, 5));

            /* ===========================
             * 2. Velocidade (20 a 200 km/h)
             * =========================== */

            double velocidade = 20 + random.nextDouble() * 180;

            /* ===========================
             * 3. Consumo dependente da velocidade
             * Se vel = 20 → 18
             * Se vel = 200 → 6
             * =========================== */

            double consumo = (-0.0666667 * velocidade) + 19.3333;
            consumo = clamp(consumo, 5, 20);

            /* ===========================
             * 4. Combustível (%)
             * =========================== */

         // Busca a última telemetria do veículo
            Double combustivelAtual = telemetriaRepository
                    .findTopByVeiculoOrderByDataHoraDesc(v)
                    .map(Telemetria::getCombustivel)
                    .orElse(100.0); // se não existir, começa cheio

            // Consumo de combustível proporcional ao consumo do carro
            double consumoCombustivel = consumo * 0.02; // ajuste fino aqui

            double novoCombustivel = combustivelAtual - consumoCombustivel;

            // Se zerar → abastece automaticamente
            if (novoCombustivel <= 0) {
                novoCombustivel = 100.0;
            }

            // Garante limites
            novoCombustivel = clamp(novoCombustivel, 0, 100);

            t.setCombustivel(round(novoCombustivel, 1));

            /* ===========================
             * 5. Arredondamentos
             * =========================== */

            t.setVelocidade(round(velocidade, 1));
            t.setConsumo(round(consumo, 1));

            telemetriaRepository.save(t);
            alertaService.verificarAlertas(t);
        }
    }

    /* ===========================
     * UTILITÁRIOS
     * =========================== */

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private double round(double value, int casas) {
        double fator = Math.pow(10, casas);
        return Math.round(value * fator) / fator;
    }
}

