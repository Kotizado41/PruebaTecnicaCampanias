package com.pruebatecnicacampanias.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.pruebatecnicacampanias.interfaces.ProducerService;
import com.pruebatecnicacampanias.model.SmsDetalle;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService implements ProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publicarDetalles(List<SmsDetalle> detalles, Long idCampania) {
        if (detalles == null || detalles.isEmpty() || idCampania == null) {
            log.warn("No se publicará mensaje: detalles vacíos o idCampania nulo");
            return;
        }

        Map<String, Object> payload = new HashMap<>();
        payload.put("idCampania", idCampania);
        payload.put("detalles", detalles);

        kafkaTemplate.send("campanias-topic", payload);
        log.info("Publicado mensaje para campaña {} con {} detalles", idCampania, detalles.size());
    }
}
