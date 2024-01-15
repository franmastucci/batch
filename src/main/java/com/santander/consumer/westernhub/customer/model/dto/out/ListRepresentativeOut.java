package com.santander.consumer.westernhub.customer.model.dto.out;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.santander.consumer.westernhub.customer.model.dto.out.error.ErrorMessageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "ListRepresentativeOut", description = "Representation of the list of representative")

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
public class ListRepresentativeOut {

    /** The representatives. */
    private List<Representative> representatives;

    /** The errors. */
    @JsonProperty("errors")
    private List<ErrorMessageDTO> errors;

}

