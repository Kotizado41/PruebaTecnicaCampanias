package com.pruebatecnicacampanias.interfaces;

import java.util.List;

import com.pruebatecnicacampanias.model.SmsDetalle;

public interface ProducerService {

    void publicarDetalles(List<SmsDetalle> detalles, Long Id);

}
