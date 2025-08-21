package br.gov.caixa.Simulador;

import br.gov.caixa.Simulador.config.external.ExternalDataSourceConfig;
import br.gov.caixa.Simulador.config.local.LocalDataSourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SimuladorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimuladorApplication.class, args);
	}

}
