package com.pruebatecnicacampanias.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pruebatecnicacampanias.interfaces.CampaniaService;
import com.pruebatecnicacampanias.interfaces.ProducerService;
import com.pruebatecnicacampanias.model.SmsDetalle;
import com.pruebatecnicacampanias.model.SmsMaestro;
import com.pruebatecnicacampanias.repository.SmsDetalleRepository;
import com.pruebatecnicacampanias.repository.SmsMaestroRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CampaniaServiceImpl implements CampaniaService {

    private final SmsMaestroRepository maestroRepository;
    private final SmsDetalleRepository detalleRepository;
    private final ProducerService producerService;
    @Value("${campanias.page-size:500}")
    private int pageSize;

    @Override
    public void iniciarProcesamiento(String fecha) {

        // LocalDate fechaEnvio = LocalDate.parse(fecha);
        LocalDate fechaEnvio = LocalDate.parse("2025-03-01");
        log.info(fecha + "::::fecha formateada:::: " + fechaEnvio);
        List<SmsMaestro> smsMaestros = maestroRepository.findByFechaEnvio(fechaEnvio);
        log.info("smsMaestros::::" + smsMaestros);
        if (smsMaestros.isEmpty()) {
            log.warn("No se encontraron campañas para la fecha {}", fecha);
            return;
        }

        for (SmsMaestro smsMaestro : smsMaestros) {

            int page = 0;
            Page<SmsDetalle> detallesPage;

            do {
                detallesPage = detalleRepository.findByMaestro(smsMaestro, PageRequest.of(page, pageSize));
                if (detallesPage.hasContent()) {
                    log.info("Publicando página {} de campaña {}", page, smsMaestro.getId());
                    producerService.publicarDetalles(detallesPage.getContent(), smsMaestro.getId());
                }
                page++;
            } while (detallesPage.hasNext());

        }
    }
}
