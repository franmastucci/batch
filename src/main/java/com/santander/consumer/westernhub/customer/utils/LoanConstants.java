package com.santander.consumer.westernhub.customer.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Contains API constants.
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoanConstants {

    /** The Constant REQUEST_MAPPING_PATH_REGISTER_LEGAL_PERSON. */
    public static final String REQUEST_MAPPING_PATH_REGISTER_LEGAL_PERSON = "/customers/register";

    /** The offset QUERY_OFFSET **/
    public static final String QUERY_OFFSET = "&_offset=";

    /** The offset  DOCUMENT_TYPE_PARAM **/
    public static final String DOCUMENT_TYPE_PARAM = "fdocumentType";

    /** The Constant REQUEST_MAPPING_PATH_REPRESENTATIVES. */
    public static final String REQUEST_MAPPING_PATH_REPRESENTATIVES = "/customers/representatives";

    /** The Constant REQUEST_MAPPING_PATH_DEALER_DATA. */
    public static final String REQUEST_MAPPING_PATH_DEALER_DATA = "/dealer/{establishmentCode}/operations/{operationId}";

    /** The Constant GET_DEALER_DATA_INFO. */
    public static final String GET_DEALER_DATA_INFO = "getDealerData";

    /** The Constant GET_VARIABLE_INTEREST_INFO. */
    public static final String GET_VARIABLE_INTEREST_INFO = "getVariableInterest";

    /** The Constant GET_LEGAL_PERSON_INFO. */
    public static final String GET_LEGAL_PERSON_INFO = "getLegalPerson";

    /** The Constant GET_LEGAL_PERSON_INFO. */
    public static final String GET_REPRESENTATIVES_INFO = "getRepresentatives";

    /** The Constant GET_DEALER_DATA. */
    public static final String GET_DEALER_DATA = "/dealerData";

    /** The Constant GET_VARIABLE_INTEREST. */
    public static final String GET_VARIABLE_INTEREST = "/variableInterest";

    /** The Constant REPRESENTATIVES_INPUT_DATE_FORMAT. */
    public static final String REPRESENTATIVES_INPUT_DATE_FORMAT = "yyyyMMdd";

    /** The Constant VARIABLE_INTEREST_INPUT_DATE_FORMAT. */
    public static final String VARIABLE_INTEREST_INPUT_DATE_FORMAT = "ddMMyyyy";

    /** The Constant OUTPUT_DATE_FORMAT. */
    public static final String OUTPUT_DATE_FORMAT = "yyyy-MM-dd";

    /** The Constant LIST_INTERVENERS. */
    public static final String LOAN_INTERVENERS = "/interveners";

    /** The Constant GET_LIST_INTERVENERS. */
    public static final String GET_LIST_INTERVENERS = "getListInterveners";

    /** The Constant GET_LIST_OPERATIONS. */
    public static final String GET_LIST_OPERATIONS = "getListOperations";

    /** The Constant GET_NORMALIZATION. */
    public static final String GET_NORMALIZATION = "getNormalization";

    /** The Constant AS400. */
    public static final String AS400 = "AS400";

    /** The Constant OPERATION_ID. */
    public static final String OPERATION_ID = "{operationId}";

    /** The DEFAULT_LANGUAGE **/
    public static final String DEFAULT_LANGUAGE = "en";

    /** The offset queryParam **/
    public static final String queryOffset = "&_offset=";

    /** The normalizationNotFoundMessage **/
    public static final String normalizationNotFoundMessage = "No description found for type: ";

    /** The Constant ERROR_GENERIC. */
    public static final String ERROR_GENERIC = "An error has occurred, please try again later";

    /** The Constant NOT_FOUND_CODE_ERROR. */
    public static final String NOT_FOUND_CODE_ERROR = "An error has occurred, not found code error";

    /** The Constant CODE ERROR 500. */
    public static final String CODE_ERROR_500 = "0000500";

    /** The Constant X-SANTANDER-SOCIETY. */
    public static final String X_SANTANDER_SOCIETY = "x_santander_society";

    /** The Constant DOCUMEMNT_ID. */
    public static final String DOCUMEMNT_ID = "documentId";

    /** The Constant X-SANTANDER-SOCIETY. */
    public static final String X_SANTANDER_OFFICE = "x_santander_office";

    /** The Constant X-SANTANDER-AREA. */
    public static final String X_SANTANDER_AREA = "x_santander_area";

    /** The Constant LANGUAGES. */
    public static final String LANGUAGES = "LANGUAGES";

    /** The Constant COUNTRIES. */
    public static final String COUNTRIES = "COUNTRIES";

    /** The Constant IDENTIFICATION_TYPE. */
    public static final String IDENTIFICATION_TYPE = "IDENTIFICATION_TYPE";

    /** The Constant ISO_CODE_ALPHA_2. */
    public static final String ISO_CODE_ALPHA_2 = "ISO_CODE_2";

    /** The Constant ISO_CODE_ALPHA_3. */
    public static final String ISO_CODE_ALPHA_3 = "ISO_CODE_3";

    /** The Constant ISO_CODE_ALPHA_3. */
    public static final String IDENTIFICATION_CODE = "IDENTIFICATION_CODE";

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

    /** The Constant HTTP_CODE_CREATED. */
    public static final String HTTP_CODE_CREATED = "201";

    /** The Constant HTTP_CODE_NO_CONTENT. */
    public static final String HTTP_CODE_NO_CONTENT = "204";

    /** The Constant RETRY. */
    public static final String RETRY = "RETRY";

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
