package com.pruebatecnicacampanias.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "sms_maestro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SmsMaestro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_maestro")
    private Long id;

    @Column(name = "nombre_campania",nullable = false)
    private String nombreCampania;

    @Column(name = "fecha_envio",nullable = false)
    private LocalDate fechaEnvio;

    @OneToMany(mappedBy = "smsMaestro",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<SmsDetalle> detalles;





}
