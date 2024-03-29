package com.santander.consumer.westernhub.customer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "codificacion", schema = "genesys")
@Getter
@Setter
@ToString
public class Codification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_codif")
    private Integer idCodif;

    @Column(name = "id_gestion", nullable = false)
    private String idGestion;

    @Column(name = "id_grabacion", nullable = false)
    private String idGrabacion;

    @Column(name = "operacion")
    private String operacion;

    @Column(name = "documento_real", nullable = false) // traducir campos a inglés
    private String documentoReal;

    @Column(name = "cod_flujo", nullable = false)
    private Integer codFlujo;

    @Column(name = "tipo", nullable = false)
    private Integer tipo;

    @Column(name = "subtipo", nullable = false)
    private Integer subtipo;

    @Column(name = "razon", nullable = false)
    private Integer razon;

    @Column(name = "comentarios", nullable = false, length = 1000)
    private String comentarios;

    @Column(name = "fec_e_gestion")
    private Timestamp fecEGestion;

    @Column(name = "hora_e_gestion")
    private Timestamp horaEGestion;

    @Column(name = "fec_s_gestion")
    private Timestamp fecSGestion;

    @Column(name = "hora_s_gestion")
    private Timestamp horaSGestion;

    @Column(name = "agente", nullable = false)
    private String agente;

    @Column(name = "login_acd", nullable = false)
    private String loginAcd;

    @Column(name = "aud_usr_creation", nullable = false)
    private String audUsrCreation;

    @Column(name = "aud_tim_creation", nullable = false)
    private Timestamp audTimCreation;

    @Column(name = "aud_usr_modification")
    private String audUsrModification;

    @Column(name = "aud_tim_modification")
    private Timestamp audTimModification;
}

