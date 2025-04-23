package com.pruebatecnicacampanias.controller;

import com.pruebatecnicacampanias.service.CampanaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class campanaController {

    private final CampanaService campanaService;

    @GetMapping("/procesar-datos/{fecha}")
    public ResponseEntity<String> procesarCampanas(@PathVariable String fecha) {
        log.debug("Fecha ingresada al controlador {}", fecha);
        campanaService.iniciarProcesamiento(fecha);
        return ResponseEntity.ok("Procesamiento iniciado para la fecha: " + fecha);

    }

}
