package com.pruebatecnicacampanias.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "nombre_campania", nullable = false)
    private String nombreCampania;

    @Column(name = "fecha_envio", nullable = false)
    private LocalDate fechaEnvio;

    @JsonManagedReference
    @OneToMany(mappedBy = "maestro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SmsDetalle> detalles;
}
