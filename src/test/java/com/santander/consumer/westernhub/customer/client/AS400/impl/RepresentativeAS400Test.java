package com.santander.consumer.westernhub.customer.client.AS400.impl;

import com.santander.ademma.common.exception.RetryException;
import com.santander.ademma.common.model.dto.context.ExecutionContext;

import com.santander.consumer.westernhub.customer.client.RepresentativeAS400;
import com.santander.consumer.westernhub.customer.config.RepresentativeConfig;
import com.santander.consumer.westernhub.customer.model.dto.RepresentativeInDTO;
import com.santander.consumer.westernhub.customer.utils.TestUtils;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;


import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * The Class RepresentativeAS400Test.
 *
 */
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@ComponentScan(basePackages = { "com.santander.consumer.westernhub.customer" })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RepresentativeAS400Test {

    /** The Representative AS400. */
    @Autowired
    private RepresentativeAS400 representativeAS400;

    /** The rest template. */
    @MockBean
    @Qualifier("restTemplateSSL")
    private RestTemplate restTemplate;

    /** The Representative config. */
    @MockBean
    private RepresentativeConfig representativeConfig;

    /** The execution context. */
    private final ExecutionContext context = new ExecutionContext();


    /**
     * Test get  Representative list of AS400.
     *
     * @throws IOException     the io exception
     */

    @Test
    void testGetListRepresentative() throws IOException, URISyntaxException {
        var representativeDTO = new RepresentativeInDTO("E", "MB", "F", "0001441", "10032023","opId");

        var json = TestUtils.fileToJson("mocks/endpoints/RepresentativeDTO-Mockeado.json");

        when(representativeConfig.getRepresentativeEndpoint()).thenReturn("/api/customers/representatives");

        when(restTemplate.exchange(any(RequestEntity.class), eq(String.class)))
                .thenReturn(new ResponseEntity<>(json, HttpStatus.OK));

        var r = representativeAS400.getRepresentative(context, representativeDTO);
        assertTrue(r.getStatusCode().is2xxSuccessful());
        assertEquals(json, r.getBody());
    }


    /**
     * Test fall back retry method.
     */

    @Test
    void testRetryExceptionErrorDescription()  {
        var representativeDTO = new RepresentativeInDTO("E", "MB", "F", "0001441", "10032023","opId");

        try {
            representativeAS400.getRepresentative(context, representativeDTO);
        } catch (RetryException | URISyntaxException ex){
            assertNotNull(ex);
            assertEquals("An error has occurred, please try again later", ex.getMessage());
        }

    }

    /**
     * Test fall back retry method.
     */

    @Test
    void testRetryExceptionWithStatus400() throws IOException {

        var representativeDTO = new RepresentativeInDTO("E", "MB", "F", "0001441", "10032023","opId");

        var jsonError = TestUtils.fileToJson("mocks/ErrorJson.json");

        when(restTemplate.exchange(any(RequestEntity.class), eq(String.class)))
                .thenThrow(new RestClientResponseException(jsonError, HttpStatus.BAD_REQUEST, "Error 400 Bad Request", HttpHeaders.EMPTY,null,null));

        when(representativeConfig.getRepresentativeEndpoint()).thenReturn("/api/customers/representatives");

        try {
            representativeAS400.getRepresentative(context, representativeDTO);
        } catch (RetryException | URISyntaxException ex){
            assertNotNull(ex);
        }

    }

    /**
     * Test fall back retry method.
     */

    @Test
    void testRetryExceptionWithStatus502() throws IOException {

        var representativeDTO = new RepresentativeInDTO("E", "MB", "F", "0001441", "10032023","opId");

        var jsonError = TestUtils.fileToJson("mocks/ErrorJson.json");

        when(restTemplate.exchange(any(RequestEntity.class), eq(String.class)))
                .thenThrow(new RestClientResponseException(jsonError, HttpStatus.BAD_GATEWAY, "Error 502 Bad Gateway", HttpHeaders.EMPTY,null,null));

        when(representativeConfig.getRepresentativeEndpoint()).thenReturn("/api/customers/representatives");

        try {
            representativeAS400.getRepresentative(context, representativeDTO);
        } catch (RetryException | URISyntaxException ex){
            assertNotNull(ex);
        }

    }

}

