package br.gov.caixa.Simulador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SimuladorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimuladorApplication.class, args);
	}

}
