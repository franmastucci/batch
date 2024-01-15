package com.santander.consumer.westernhub.customer.model.dto.out;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "Representatives", description = "Representation of representatives")

/**
 * Gets the additional properties.
 *
 * @return the additional properties
 */
@Getter

/**
 * Sets the additional properties.
 *
 * @param additionalProperties the additional properties
 */
@Setter

/**
 * Hash code.
 *
 * @return the int
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class Representative {

    /** The documentType. */
    private String documentType;

    /** The documentId. */
    private String documentId;

    /** The representativeOrderNumber. */
    private Integer representativeOrderNumber;

    /** The positionDescription. */
    private String positionDescription;

    /** The datePositionSeniority. */
    private String datePositionSeniority;

    /** The notaryName. */
    private String notaryName;

    /** The notarySurname. */
    private String notarySurnames;

    /** The provinceCode. */
    private Integer provinceCode;

}

