package com.santander.consumer.westernhub.customer.model.dto.out.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * The Class ErrorListMessageDTO.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "Errors", description = "ErrorListMessageDTO data")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ErrorListMessageDTO implements Serializable {

    /** The Constant serialVersionUID. */
    @Serial
    private static final long serialVersionUID = 1L;

    /** The code list. */
    @JsonProperty("errors")
    private List<ErrorMessageDTO> errors;

}
