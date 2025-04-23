package com.pruebatecnicacampanias.repository;

import com.pruebatecnicacampanias.model.SmsMaestro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SmsMaestroRepository extends JpaRepository<SmsMaestro,Long> {

    List<SmsMaestro> findByFechaEnvio(LocalDate fechaEnvio);

}
