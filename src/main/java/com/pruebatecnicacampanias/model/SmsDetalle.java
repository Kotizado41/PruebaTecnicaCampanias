package com.pruebatecnicacampanias.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sms_detalle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmsDetalle {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_maestro", nullable = false)
    private SmsMaestro smsMaestro;

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "mensaje", nullable = false, length = 500)
    private String mensaje;

    @Column(name = "enviado", nullable = false)
    private Boolean enviado;


}
