package com.santander.consumer.westernhub.customer.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Contains API constants.
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RepresentativesConstants {


    /** The Constant CUSTOMERS_PATH. */
    public static final String CUSTOMERS_PATH = "/customers";

    /** The Constant REQUEST_MAPPING_PATH_REPRESENTATIVES. */
    public static final String REQUEST_MAPPING_PATH_REPRESENTATIVES = CUSTOMERS_PATH + "/representatives";


    /** The Constant X-SANTANDER-SOCIETY. */
    public static final String X_SANTANDER_SOCIETY = "x_santander_society";


    /** The Constant X-SANTANDER-SOCIETY. */
    public static final String X_SANTANDER_OFFICE = "x_santander_office";

    /** The Constant X-SANTANDER-AREA. */
    public static final String X_SANTANDER_AREA = "x_santander_area";

    public static String JDBC_URL = "jdbc:postgresql://cwed1airrdaoptlccgene001.cluster-cdui04e4qtuy.eu-west-1.rds.amazonaws.com:5432/CWED1AIRRDAOPTLCCGENE001_DDB01";
    public static String USER = "postgres";
    public static String PASS = "M30-uS5-?Uw0Nr4q*SYG%";
    public static String NA = "N/A";
    public static String AUD_USR_CREATION = "Historico";
    public static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_PATTERN = "MM/dd/yyyy";
    public static String TIME_PATTERN = "HH:mm:ss";
    public static final String INSERT_CODIFICACION_SQL = "INSERT_CODIFICACION_1.sql";
    public static final String INSERT_IVR_CODIFICACION_SQL = "INSERT_IVR_CODIFICACION_1.sql";
    public static final String COMPLETED_PROCESS_LOG = "Proceso completado";
    public static final int ROW_LIMIT = 1;
    public static final String USER_DIR = "user.dir";
    public static final String INSERT_CODIFICATION_BASE_QUERY = "INSERT INTO genesys.codificacion (id_gestion, agente, login_acd, id_grabacion, fec_e_gestion, hora_e_gestion, fec_s_gestion, hora_s_gestion, operacion, tipo, subtipo, razon, documento_real, cod_flujo, comentarios, aud_usr_creation, aud_tim_creation) VALUES ";
    public static final String INSERT_IVR_CODIFICACION_BASE_QUERY = "INSERT INTO genesys.ivr_codificacion(id_grabacion, servicio, vdn, via_entrada, cod_afiliacion, tipo_llamante, aud_usr_creation, aud_tim_creation) VALUES ";


}
