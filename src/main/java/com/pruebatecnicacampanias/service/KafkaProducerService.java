package com.pruebatecnicacampanias.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String,String> kafkaTemplate;

    public void enviarMensaje(String topic,String mensaje){

        kafkaTemplate.send(topic,mensaje);
        log.info("Mensaje enviado a kafka - topic {}, Mensaje: {}",topic,mensaje);

    }
}
