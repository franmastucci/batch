package com.santander.consumer.westernhub.customer.service.clients;

import com.santander.ademma.common.model.dto.context.ExecutionContext;
import com.santander.consumer.westernhub.customer.model.dto.in.RepresentativeDTO;
import com.santander.consumer.westernhub.customer.model.dto.out.ListRepresentativeOut;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * The Interface RepresentativeService.
 */
public interface RepresentativeService {

    /**
     * Gets the Representative
     *
     * @param executionContext the execution context
     * @param representativeDTO the representativeDTO
     * @return a list of operations
     * @throws URISyntaxException the URI syntax exception
     */

    public ListRepresentativeOut getRepresentative(ExecutionContext executionContext, RepresentativeDTO representativeDTO) throws URISyntaxException, IOException;


}