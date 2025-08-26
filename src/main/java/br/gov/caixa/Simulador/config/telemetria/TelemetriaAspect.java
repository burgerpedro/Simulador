package br.gov.caixa.Simulador.config.telemetria;

import br.gov.caixa.Simulador.service.TelemetriaService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TelemetriaAspect {

    @Autowired
    private TelemetriaService telemetriaService;

    @Around("execution(* br.gov.caixa.Simulador.controller.SimulacaoController.*(..))")
    public Object monitorarMetodosController(ProceedingJoinPoint joinPoint) throws Throwable {
        long inicio = System.currentTimeMillis();
        Object resultado = joinPoint.proceed();
        long fim = System.currentTimeMillis();
        long duracao = fim - inicio;

        String nomeEndpoint = joinPoint.getSignature().getName();

        int statusHttp = 500;
        if (resultado instanceof ResponseEntity<?> response) {
            statusHttp = response.getStatusCode().value();
        }

        telemetriaService.atualizarMetricas(nomeEndpoint, duracao, statusHttp);

        return resultado;
    }
}
