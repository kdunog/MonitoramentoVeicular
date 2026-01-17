package com.MonitoramentoVeicular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MonitoramentoVeicularApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoramentoVeicularApplication.class, args);
	}

}
