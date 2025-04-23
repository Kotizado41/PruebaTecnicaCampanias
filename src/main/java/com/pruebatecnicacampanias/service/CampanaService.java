package com.pruebatecnicacampanias.service;

import com.pruebatecnicacampanias.model.SmsDetalle;
import com.pruebatecnicacampanias.model.SmsMaestro;
import com.pruebatecnicacampanias.repository.SmsDetalleRepository;
import com.pruebatecnicacampanias.repository.SmsMaestroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CampanaService {
    private final SmsMaestroRepository smsMaestroRepository;
    private final SmsDetalleRepository smsDetalleRepository;
    private final KafkaProducerService kafkaProducerService;

    public void iniciarProcesamiento(String fecha) {

        log.info("Iniciando Procesamiento para la fecha:{}", fecha);
        LocalDate fechaProcesamiento = LocalDate.parse(fecha);

        List<SmsMaestro> campanias = smsMaestroRepository.findByFechaEnvio(fechaProcesamiento);

        for (SmsMaestro campania : campanias) {
            log.info("Procesando campaña ID: {}", campania.getId());

            List<SmsDetalle> detalles = smsDetalleRepository.findBySmsMaestro_Id(campania.getId());

            for (SmsDetalle detalle : detalles) {
                kafkaProducerService.enviarMensaje("campanias-topic", detalle.getMensaje());
                log.debug("Detalle ID {} enviado a kafka", detalle.getId());
            }

            log.info("Campaña ID {} procesada con {} detalles", campania.getId(), detalles.size());

        }
        log.info("Procesamiento finalizado para la fecha: {}", fecha);
    }

}
