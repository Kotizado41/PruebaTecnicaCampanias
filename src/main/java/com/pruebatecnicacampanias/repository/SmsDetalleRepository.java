package com.pruebatecnicacampanias.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pruebatecnicacampanias.model.SmsDetalle;
import com.pruebatecnicacampanias.model.SmsMaestro;

@Repository
public interface SmsDetalleRepository extends JpaRepository<SmsDetalle, Long> {

    Page<SmsDetalle> findByMaestro(SmsMaestro maestro, Pageable pageable);

}
