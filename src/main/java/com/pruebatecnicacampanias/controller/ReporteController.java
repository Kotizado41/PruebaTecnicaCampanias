package com.pruebatecnicacampanias.controller;

import java.io.File;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/reportes")
@Slf4j
public class ReporteController {
    @GetMapping("/estado/{idCampania}")
    public ResponseEntity<?> estadoReporte(@PathVariable Long idCampania) {
        File file = new File("reportes/reporte_campania_" + idCampania + ".csv");
        if (file.exists()) {
            return ResponseEntity.ok("{\"estado\":\"generado\"}");
        } else {
            return ResponseEntity.ok("{\"estado\":\"procesando\"}");
        }
    }

    @GetMapping("/descargar/{idCampania}")
    public ResponseEntity<FileSystemResource> descargarReporte(@PathVariable Long idCampania) {
        File file = new File("reportes/reporte_campania_" + idCampania + ".csv");
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new FileSystemResource(file));
    }
}
