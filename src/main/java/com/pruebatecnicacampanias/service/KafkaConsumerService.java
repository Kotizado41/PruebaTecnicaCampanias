package com.pruebatecnicacampanias.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pruebatecnicacampanias.model.SmsDetalle;
import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class KafkaConsumerService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "campanias-topic", groupId = "grupo-campanias")
    public void consumir(ConsumerRecord<String, Object> record) {
        log.info("Mensaje recibido: {}", record.value());

        try {
            Map<String, Object> data = objectMapper.convertValue(record.value(), Map.class);

            if (!data.containsKey("idCampania") || !data.containsKey("detalles")) {
                log.error("Mensaje inválido, falta idCampania o detalles.");
                return;
            }

            Long idCampania = Long.parseLong(data.get("idCampania").toString());

            List<SmsDetalle> detalles = objectMapper.convertValue(
                    data.get("detalles"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, SmsDetalle.class));

            if (detalles == null || detalles.isEmpty()) {
                log.warn("La campaña {} no tiene detalles para procesar.", idCampania);
                return;
            }

            String directorio = "reportes";
            File dir = new File(directorio);
            if (!dir.exists())
                dir.mkdirs();

            String path = directorio + "/reporte_campania_" + idCampania + ".csv";
            File reporte = new File(path);
            boolean existe = reporte.exists();

            // Usa FileOutputStream para escribir BOM solo una vez
            try (FileOutputStream fos = new FileOutputStream(reporte, true);
                    OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                    BufferedWriter writer = new BufferedWriter(osw)) {

                if (!existe) {
                    // Escribir BOM
                    fos.write(0xEF);
                    fos.write(0xBB);
                    fos.write(0xBF);
                    writer.write("Telefono;Mensaje;Enviado\n");
                }

                for (SmsDetalle detalle : detalles) {
                    writer.write(detalle.getTelefono() + ";" +
                            detalle.getMensaje() + ";" +
                            detalle.isEnviado() + "\n");
                }
            }

            log.info("Reporte generado para campaña {}", idCampania);

        } catch (Exception e) {
            log.error("Error al procesar el mensaje de Kafka", e);
        }
    }

}