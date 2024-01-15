package com.santander.consumer.westernhub.customer.model.dto.in;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

/**
 * The Class DealerDataDTO.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "DealerDataDTO", description = "DealerDataDTO data")
@Getter
@EqualsAndHashCode(callSuper = false)
public class DealerDataDTO implements Serializable {

    public DealerDataDTO(String sociedad, String sucursal, String area, String documentId, String operationId, String documentType) {
        super();
        this.sociedad = sociedad;
        this.sucursal = sucursal;
        this.area = area;
        this.documentId = documentId;
        this.documentType =  documentType;
        this.operationId = operationId;
    }

    /** The Constant serialVersionUID. */
    @Serial
    private static final long serialVersionUID = 1L;

    /** The sociedad. */
    private String sociedad;

    /** The sucursal. */
    private String sucursal;

    /** The area. */
    private String area;

    /** The documentId. */
    private String documentId;

    /** The documentType. */
    private String documentType;

    /** The operationId. */
    private String operationId;

}

