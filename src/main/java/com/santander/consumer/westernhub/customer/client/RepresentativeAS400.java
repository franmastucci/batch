package com.santander.consumer.westernhub.customer.client;

import com.santander.consumer.westernhub.customer.model.dto.RepresentativeInDTO;
import org.springframework.http.ResponseEntity;
import com.santander.ademma.common.model.dto.context.ExecutionContext;
import java.net.URISyntaxException;

/**
 * The Interface RepresentativeAS400.
 */
public interface RepresentativeAS400 {

    /**
     * Gets the Representative.
     *
     * @param context the context
     * @param representativeInDTO the representativeDTO
     * @return ResponseEntity
     * @throws URISyntaxException the URI syntax exception
     */
    public ResponseEntity<String> getRepresentative(ExecutionContext context, RepresentativeInDTO representativeInDTO) throws URISyntaxException;


}

