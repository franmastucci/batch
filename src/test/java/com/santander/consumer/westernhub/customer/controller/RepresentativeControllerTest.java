package com.santander.consumer.westernhub.customer.controller;

import com.santander.ademma.common.model.dto.context.ExecutionContext;

import com.santander.consumer.westernhub.customer.model.dto.RepresentativeInDTO;
import com.santander.consumer.westernhub.customer.model.dto.out.ListRepresentativeOut;
import com.santander.consumer.westernhub.customer.model.dto.out.Representative;
import com.santander.consumer.westernhub.customer.model.dto.out.error.ErrorMessageDTO;
import com.santander.consumer.westernhub.customer.service.clients.RepresentativeService;
import com.santander.consumer.westernhub.customer.utils.RepresentativesConstants;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The Class RepresentativeController.
 *
 */
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@ComponentScan(basePackages = { "com.santander.consumer.westernhub.customer" })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RepresentativeControllerTest {

    /** The mock mvc. */
    @Autowired
    private MockMvc mockMvc;

    /** The Representatives AS400 service. */
    @MockBean
    private RepresentativeService representativeService;


    /**
     * Test get Representative.
     *
     * @throws Exception the exception
     */
    @Test
    void testGetRepresentativeNoContent() throws Exception {

        when(representativeService.getRepresentative(
                any(ExecutionContext.class),
                any(RepresentativeInDTO.class)
        )).thenReturn(null);

        mockMvc.perform(get(RepresentativesConstants.REQUEST_MAPPING_PATH_REPRESENTATIVES+"?documentType=L&operationId=E12F20230148228")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, AUTHORIZATION)
                        .header("x-santander-society", RepresentativesConstants.X_SANTANDER_SOCIETY)
                        .header("x-santander-office", RepresentativesConstants.X_SANTANDER_OFFICE)
                        .header("x-santander-area", RepresentativesConstants.X_SANTANDER_AREA)
                        .header("documentId", "E12F20230148228"))
                .andExpect(status().isNoContent());

    }

    /**
     * Test get LegalPerson.
     *
     * @throws Exception the exception
     */
    @Test
    void testGetRepresentativeOk() throws Exception {

        List<Representative> repList = new ArrayList<>();

        Representative representative = new Representative("doctype","documentid",1, "positionDescription", "datePositionSeniority", "notaryName", "notarySurnames", 1);
        repList.add(representative);
        List<ErrorMessageDTO> errors = new ArrayList<>();
        when(representativeService.getRepresentative(
                any(ExecutionContext.class),
                any(RepresentativeInDTO.class)
        )).thenReturn(new ListRepresentativeOut(repList, errors));

        mockMvc.perform(get(RepresentativesConstants.REQUEST_MAPPING_PATH_REPRESENTATIVES+"?documentType=L&operationId=E12F20230148228")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, AUTHORIZATION)
                        .header("x-santander-society", RepresentativesConstants.X_SANTANDER_SOCIETY)
                        .header("x-santander-office", RepresentativesConstants.X_SANTANDER_OFFICE)
                        .header("x-santander-area", RepresentativesConstants.X_SANTANDER_AREA)
                        .header("documentId", "E12F20230148228"))
                .andExpect(status().isOk());
    }

}


