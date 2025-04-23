package com.pruebatecnicacampanias.repository;

import com.pruebatecnicacampanias.model.SmsDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmsDetalleRepository extends JpaRepository<SmsDetalle,Long> {

    List<SmsDetalle> findBySmsMaestro_Id(Long idMaestro);

}
