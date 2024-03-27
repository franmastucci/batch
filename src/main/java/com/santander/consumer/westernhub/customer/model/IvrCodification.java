package com.santander.consumer.westernhub.customer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "ivr_codificacion", schema = "genesys")
@Getter
@Setter
@ToString
public class IvrCodification {

    @Id
    @Column(name = "id_grabacion", nullable = false)
    private String idGrabacion;

    @Column(name = "fec_e_llamada")
    private Timestamp fecELlamada;

    @Column(name = "hora_e_llamada")
    private Timestamp horaELlamada;

    @Column(name = "fec_s_llamada")
    private Timestamp fecSLlamada;

    @Column(name = "hora_s_llamada")
    private Timestamp horaSLlamada;

    @Column(name = "via_entrada", nullable = false, length = 50)
    private String viaEntrada;

    @Column(name = "servicio", nullable = false, length = 50)
    private String servicio;

    @Column(name = "vdn", nullable = false, length = 50)
    private String vdn;

    @Column(name = "cod_afiliacion", nullable = false, length = 6)
    private String codAfiliacion;

    @Column(name = "tipo_llamante", nullable = false, length = 9)
    private String tipoLlamante;

    @Column(name = "aud_usr_creation", nullable = false)
    private String audUsrCreation;

    @Column(name = "aud_tim_creation", nullable = false)
    private Timestamp audTimCreation;

    @Column(name = "aud_usr_modification")
    private String audUsrModification;

    @Column(name = "aud_tim_modification")
    private Timestamp audTimModification;
}
