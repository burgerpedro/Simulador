package br.gov.caixa.Simulador.config.telemetria;

import br.gov.caixa.Simulador.service.TelemetriaService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TelemetriaAspect {

    @Autowired
    private TelemetriaService telemetryService;

    @Around("execution(* br.gov.caixa.Simulador.controller.SimulacaoController.*(..))")
    public Object profileAllSimuladorControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        long duration = end - start;

        String endpointName = joinPoint.getSignature().getName();

        telemetryService.updateMetrics(endpointName, duration);

        return result;
    }
}
