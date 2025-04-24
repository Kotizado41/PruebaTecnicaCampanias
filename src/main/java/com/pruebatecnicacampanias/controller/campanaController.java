package com.pruebatecnicacampanias.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pruebatecnicacampanias.service.CampaniaServiceImpl;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class CampanaController {

    private final CampaniaServiceImpl campanaService;

    @GetMapping("/procesar-datos/{fecha}")
    public ResponseEntity<String> procesarCampanas(@PathVariable String fecha) {
        log.info("Solicitud de procesamiento de campañas para la fecha: {}", fecha);
        campanaService.iniciarProcesamiento(fecha); // Este método será asincrónico
        return ResponseEntity.ok("Procesamiento iniciado para la fecha: " + fecha);
    }
}
