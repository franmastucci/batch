package com.santander.consumer.westernhub.customer.service.clients.impl;

import com.santander.ademma.common.model.dto.context.ExecutionContext;
import com.santander.ademma.common.utils.logging.LoggingUtils;
import com.santander.consumer.westernhub.customer.client.RepresentativeAS400;
import com.santander.consumer.westernhub.customer.model.dto.in.RepresentativeDTO;
import com.santander.consumer.westernhub.customer.model.dto.out.ListRepresentativeOut;
import com.santander.consumer.westernhub.customer.service.clients.RepresentativeService;
import com.santander.consumer.westernhub.customer.service.utils.ServicesUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

/**
 * The Class RepresentativeServiceImpl.
 */
@Service


/** The Constant log. */
@Slf4j


/**
 * Instantiates a new Representative Person AS 400 service impl.
 *
 * @param representativeDTO the representativeDTO
 */
@RequiredArgsConstructor
public class  RepresentativeServiceImpl implements RepresentativeService {


    /** The hierarchy level. */
    private final RepresentativeAS400 representative;

    @Autowired
    ServicesUtils ut;


    @Override
    public ListRepresentativeOut getRepresentative(ExecutionContext executionContext, RepresentativeDTO representativeDTO) throws URISyntaxException {
        ResponseEntity<String> response =  this.representative.getRepresentative(executionContext, representativeDTO);
        LoggingUtils.logControllerResponse(log, response);
        return ut.switchStatusRepresentatives(executionContext, response);
    }

}
