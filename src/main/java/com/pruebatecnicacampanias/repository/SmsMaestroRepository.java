package com.pruebatecnicacampanias.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pruebatecnicacampanias.model.SmsMaestro;

public interface SmsMaestroRepository extends JpaRepository<SmsMaestro, Long> {
    List<SmsMaestro> findByFechaEnvio(LocalDate fechaEnvio);
}
