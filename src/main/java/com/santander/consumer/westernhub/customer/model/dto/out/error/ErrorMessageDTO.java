package com.santander.consumer.westernhub.customer.model.dto.out.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * The Class ErrorMessageDTO.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "Error", description = "ErrorMessageDTO data")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageDTO implements Serializable {

    /** The Constant serialVersionUID. */
    @Serial
    private static final long serialVersionUID = 1L;

    /** The code. */
    private String code;

    /** The description. */
    private String description;

    /** The level. */
    private String level;

    /** The message. */
    private String message;

    @Override
    public String toString() {
        return "ErrorMessageDTO{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", level='" + level + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
