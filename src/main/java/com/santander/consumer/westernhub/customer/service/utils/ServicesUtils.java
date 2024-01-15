package com.santander.consumer.westernhub.customer.service.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.santander.ademma.common.exception.MappingException;
import com.santander.ademma.common.model.dto.context.ExecutionContext;
import com.santander.consumer.westernhub.customer.model.dto.out.*;
import com.santander.consumer.westernhub.customer.model.dto.out.error.ErrorMessageDTO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.santander.consumer.westernhub.customer.utils.LoanConstants.*;

@Slf4j

/**
 * The Class ServicesUtils.
 */
@Service
@Data
public class ServicesUtils {

    /** The input format Representatives. */
    static DateTimeFormatter representativesInputDateFormatter = DateTimeFormatter.ofPattern(REPRESENTATIVES_INPUT_DATE_FORMAT);

    /** The input format VariableInterest. */
    static DateTimeFormatter variableIneterestInputDateFormatter = DateTimeFormatter.ofPattern(VARIABLE_INTEREST_INPUT_DATE_FORMAT);

    /** The output format. */
    static DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(OUTPUT_DATE_FORMAT);


    /**
     * Switch status.
     *
     * @param context  the context
     * @param response  the response
     * @return the gets the customers out
     */


    public ListRepresentativeOut switchStatusRepresentatives(ExecutionContext context, ResponseEntity<String> response) {
        ListRepresentativeOut dto = null;

        if (response.getStatusCode() == HttpStatus.OK) {
            var salida = ServicesUtils.normalizeSpace(response.getBody());
            dto = getObj(salida, ListRepresentativeOut.class);

            if (!ObjectUtils.isEmpty(dto.getErrors())) {
                // Si el codigo del error viene vacia se quita de la lista de errores
                dto.setErrors(checkEmptyErrors(dto.getErrors()));
            }

            if (ObjectUtils.isEmpty(dto.getErrors())) {
                dto.setErrors(null);
            }

            if(!ObjectUtils.isEmpty(dto.getRepresentatives())) {
                dto.getRepresentatives().stream().forEach(i -> {
                    if(null != i.getDatePositionSeniority() ) {
                        i.setDatePositionSeniority(representativesDateFormat(i.getDatePositionSeniority()));
                    }
                });
            }

            return dto;

        } else if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            return null;

        } else {
            var salida = ServicesUtils.normalizeSpace(response.getBody());
            dto = ServicesUtils.getObj(salida, ListRepresentativeOut.class);
            // returns all HierarchyLevel List
            return dto;
        }

    }



    /**
     * Checks if is json valid.
     *
     * @param json the json
     * @return true, if is json valid
     */
    public static boolean isJsonValid(String json) {
        var mapper = new ObjectMapper();
        try {
            mapper.readTree(json);
            return true;

        } catch (JsonProcessingException e) {

            return false;
        }

    }

    public static <T> T getObj(String json, Class<T> clazz) {
        var mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, clazz);

        } catch (JsonProcessingException e) {

            throw new MappingException(e.getMessage());
        }

    }

    public static String representativesDateFormat(String dateString) {
        try {
            var date = LocalDate.parse(dateString, representativesInputDateFormatter);
            return date.format(outputFormatter);
        } catch (Exception e) {
            return dateString;
        }
    }

    public static String variableInterestDateFormat(String dateString) {
        try {
            var date = LocalDate.parse(dateString, variableIneterestInputDateFormatter);
            return date.format(outputFormatter);
        } catch (Exception e) {
            return dateString;
        }
    }

    public static String normalizeSpace(String str){
        return StringUtils.normalizeSpace(str);
    }

    public List<ErrorMessageDTO> checkEmptyErrors(List<ErrorMessageDTO> errors) {

        List<ErrorMessageDTO> listErrors = new ArrayList<>();
        for (ErrorMessageDTO error : errors) {
            if (!ObjectUtils.isEmpty(error.getCode())) { // Si el codigo viene vacio el error no es válido
                listErrors.add(error);
            }
        }
        return listErrors;
    }

}

