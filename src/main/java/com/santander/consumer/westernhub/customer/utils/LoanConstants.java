package com.santander.consumer.westernhub.customer.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Contains API constants.
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoanConstants {

    /** The Constant REQUEST_MAPPING_PATH_REPRESENTATIVES. */
    public static final String REQUEST_MAPPING_PATH_REPRESENTATIVES = "/customers/representatives";

    /** The Constant REPRESENTATIVES_INPUT_DATE_FORMAT. */
    public static final String REPRESENTATIVES_INPUT_DATE_FORMAT = "yyyyMMdd";

    /** The Constant VARIABLE_INTEREST_INPUT_DATE_FORMAT. */
    public static final String VARIABLE_INTEREST_INPUT_DATE_FORMAT = "ddMMyyyy";

    /** The Constant OUTPUT_DATE_FORMAT. */
    public static final String OUTPUT_DATE_FORMAT = "yyyy-MM-dd";

    /** The Constant X-SANTANDER-SOCIETY. */
    public static final String X_SANTANDER_SOCIETY = "x_santander_society";

    /** The Constant DOCUMEMNT_ID. */
    public static final String DOCUMEMNT_ID = "documentId";

    /** The Constant X-SANTANDER-SOCIETY. */
    public static final String X_SANTANDER_OFFICE = "x_santander_office";

    /** The Constant X-SANTANDER-AREA. */
    public static final String X_SANTANDER_AREA = "x_santander_area";

    /** The Constant ERROR_GENERICO. */
    public static final String ERROR_GENERICO = "An error has occurred, please try again later";

    //API responseCodes
    /** The Constant HTTP_CODE_OK. */
    public static final String HTTP_CODE_OK =  "200";

    /** The Constant HTTP_CODE_NOT_FOUND. */
    public static final String HTTP_CODE_NOT_FOUND =  "404";

    /** The Constant HTTP_CODE_BAD_REQUEST. */
    public static final String HTTP_CODE_BAD_REQUEST =  "400";

    /** The Constant HTTP_CODE_UNAUTHORIZED. */
    public static final String HTTP_CODE_UNAUTHORIZED =  "401";

    /** The Constant HTTP_CODE_FORBIDDEN. */
    public static final String HTTP_CODE_FORBIDDEN = "403";

    /** The Constant HTTP_CODE_UNPROCESSABLE_ENTITY. */
    public static final String HTTP_CODE_UNPROCESSABLE_ENTITY = "422";

    /** The Constant HTTP_CODE_TOO_MANY_REQUESTS. */
    public static final String HTTP_CODE_TOO_MANY_REQUESTS = "429";

    /** The Constant HTTP_CODE_INTERNAL_SERVER_ERROR. */
    public static final String HTTP_CODE_INTERNAL_SERVER_ERROR = "500";

    /** The Constant HTTP_CODE_SERVICE_UNAVAILABLE. */
    public static final String HTTP_CODE_SERVICE_UNAVAILABLE =  "503";

    /** The Constant HTTP_CODE_NO_CONTENT. */
    public static final String HTTP_CODE_NO_CONTENT = "204";

    /** The Constant RETRY. */
    public static final String RETRY = "RETRY";

    /** The Constant GET_REPRESENTATIVES_INFO. */
    public static final String GET_REPRESENTATIVES_INFO = "getRepresentatives";


    /** The Constant getJsonError500AS400. */
    public static final String GETJSONERROR500AS400 = """
                   {
                "errors": [{
                     "code": "0000500",
                     "message": "DESCR-ERROR",
                     "level": "ERROR",
                     "description": "DESCR-ERROR"
                   }]
                }
                """;


}
