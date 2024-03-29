package com.santander.consumer.westernhub.customer.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Contains API constants.
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CodificationDataConstants {


    /** The Constant CUSTOMERS_PATH. */
    public static final String CODIFICATION_DATA_PATH = "/codification-data/v0";


    /** The Constant REQUEST_MAPPING_PATH_REPRESENTATIVES. */
    public static final String REQUEST_MAPPING_PATH_CODIFICATION = CODIFICATION_DATA_PATH + "/codification";


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

}
