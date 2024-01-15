package com.santander.consumer.westernhub.customer.client;

import com.santander.consumer.westernhub.customer.model.dto.in.RepresentativeDTO;
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
     * @param representativeDTO the representativeDTO
     * @return ResponseEntity
     * @throws URISyntaxException the URI syntax exception
     */
    public ResponseEntity<String> getRepresentative(ExecutionContext context, RepresentativeDTO representativeDTO) throws URISyntaxException;


}

