package com.santander.consumer.westernhub.customer.client.impl;

import com.santander.ademma.common.exception.RetryException;
import com.santander.ademma.common.model.dto.context.ExecutionContext;
import com.santander.ademma.common.utils.httpclient.HttpClientUtils;
import com.santander.ademma.common.utils.logging.LoggingUtils;
import com.santander.consumer.westernhub.customer.client.RepresentativeAS400;
import com.santander.consumer.westernhub.customer.config.RepresentativeConfig;
import com.santander.consumer.westernhub.customer.model.dto.in.RepresentativeDTO;
import com.santander.consumer.westernhub.customer.service.utils.ServicesUtils;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.santander.consumer.westernhub.customer.utils.LoanConstants.*;

/**
 * The Class RepresentativeAS400Impl.
 */
@Component

/** The Constant log. */
@Slf4j
public class RepresentativeAS400Impl implements RepresentativeAS400 {

    /** The rest template. */
    @Autowired
    @Qualifier("restTemplateSSL")
    private RestTemplate restTemplate;

    /** The Representative config. */
    @Autowired
    private RepresentativeConfig representativeConfig;

    @SuppressWarnings("unused")
    private ResponseEntity<String> fallbackRetry(RuntimeException exc) {
        if (exc instanceof RestClientResponseException rc) {
            if((rc.getStatusCode().value() < HttpStatus.INTERNAL_SERVER_ERROR.value())) {
                LoggingUtils.logClientResponse(log, RETRY, HttpStatus.resolve(rc.getStatusCode().value()), exc.getMessage());
                var json = exc.getMessage().substring(exc.getMessage().indexOf("\"") + 1,
                        exc.getMessage().lastIndexOf("\""));
                if (ServicesUtils.isJsonValid(json)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(json);
                }
            }
            else {
                LoggingUtils.logClientResponse(log, RETRY, HttpStatus.INTERNAL_SERVER_ERROR, exc.getMessage());
                var json = GETJSONERROR500AS400;
                return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(json);
            }

        } else {
            LoggingUtils.logClientResponse(log, RETRY, HttpStatus.INTERNAL_SERVER_ERROR, exc.getMessage());
        }
        throw new RetryException(ERROR_GENERICO);


    }

    @Override
    @Retry(name = "codeClientRT", fallbackMethod = "fallbackRetry")
    public ResponseEntity<String> getRepresentative(ExecutionContext context,
                                                         RepresentativeDTO representativeDTO) {
        // Log Client Init
        LoggingUtils.logClientInit(log, "getOperations", context.getContext(), "");

        // Get headers and URI
        HttpHeaders headers = HttpClientUtils.generateHeaders(context);

        headers.add(X_SANTANDER_OFFICE, representativeDTO.getSucursal());
        headers.add(X_SANTANDER_AREA, representativeDTO.getArea());
        headers.add(X_SANTANDER_SOCIETY, representativeDTO.getSociedad());
        headers.add(DOCUMEMNT_ID, representativeDTO.getDocumentId());

        var ur2 = UriComponentsBuilder.fromUriString(representativeConfig.getRepresentativeEndpoint());
        addParameter(ur2, "documentType", representativeDTO.getDocumentType());
        addParameter(ur2, "operationId", representativeDTO.getOperationId());

        var uri = ur2.build().encode().toUri();

        // Get requestEntity and responseEntity
        RequestEntity<Void> request = RequestEntity.get(uri).headers(headers).build();

        printLogs(uri, headers);
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        // Log client response
        LoggingUtils.logClientResponse(log, "get", HttpStatus.valueOf(response.getStatusCode().value()), response.getBody());

        // return reponseEntity
        return response;
    }

    /**
     * Adds the parameter.
     *
     * @param uriB  the uri B
     * @param param the param
     * @param value the value
     */
    private void addParameter(UriComponentsBuilder uriB, String param, String value) {
        if (!ObjectUtils.isEmpty(value)) {
            uriB.queryParam(param, value);
        }

    }

    private void printLogs (URI uri, HttpHeaders headers){
        if(log.isDebugEnabled()) {
            headers.forEach((x, y) -> LoggingUtils.logClientResponse(log, x, HttpStatus.OK, y));
            LoggingUtils.logClientResponse(log, "URI", HttpStatus.OK, uri);
        }
    }

}
