package br.gov.caixa.Simulador.service;

import com.azure.messaging.eventhubs.EventDataBatch;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import com.azure.messaging.eventhubs.EventData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.annotation.PreDestroy;

import java.io.IOException;


@Service
public class EventHubService {
    private static final Logger logger = LoggerFactory.getLogger(EventHubService.class);
    private final EventHubProducerClient producerClient;

    public EventHubService() {
        // Conexão e EntityPath do EventHub fornecidos
        String connectionString = "Endpoint=sb://eventhack.servicebus.windows.net/;SharedAccessKeyName=hack;SharedAccessKey=HeHeVaVqyVkntO2FnjQcs2Ilh/4MUDo4y+AEhKp8z+g=;EntityPath=simulacoes";
        this.producerClient = new EventHubClientBuilder()
                .connectionString(connectionString)
                .buildProducerClient();
    }

    public void enviarEvento(String jsonPayload) {
        try {
            EventDataBatch batch = producerClient.createBatch();
            EventData eventData = new EventData(jsonPayload);

            if (!batch.tryAdd(eventData)) {
                logger.error("Evento muito grande para ser enviado no batch.");
                return;
            }

            producerClient.send(batch);
            logger.info("Evento enviado com sucesso para o EventHub.");
        } catch (Exception e) {
            logger.error("Erro ao enviar evento para o EventHub", e);
        }
    }
}

