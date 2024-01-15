package com.santander.consumer.westernhub.customer.service.clients.impl;


import com.santander.ademma.common.model.dto.context.ExecutionContext;

import com.santander.consumer.westernhub.customer.client.RepresentativeAS400;
import com.santander.consumer.westernhub.customer.model.dto.in.RepresentativeDTO;
import com.santander.consumer.westernhub.customer.model.dto.out.ListRepresentativeOut;
import com.santander.consumer.westernhub.customer.service.clients.RepresentativeService;
import com.santander.consumer.westernhub.customer.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.io.IOException;
import java.net.URISyntaxException;
import static org.junit.Assert.assertNotNull;


/**
 * The Class RepresentativesServiceImplTest.
 *
 */

@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@ComponentScan(basePackages = { "com.santander.consumer.westernhub.customer" })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RepresentativesServiceImplTest {

    @Autowired
    RepresentativeService representativeService;

    /**
     * The ut.
     */
    TestUtils ut = new TestUtils();

    private ExecutionContext context = new ExecutionContext();


    /**
     * The representative.
     */
    @MockBean
    private RepresentativeAS400 representative;


    /**
     * Inits.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */

    @BeforeEach
    void init() throws IOException, URISyntaxException {
        var json = TestUtils.fileToJson("mocks/endpoints/RepresentativeDTO-Mockeado.json");
        Mockito.when(representative.getRepresentative(Mockito.any(ExecutionContext.class),Mockito.any(RepresentativeDTO.class)))
                .thenReturn(new ResponseEntity<String>(json, HttpStatus.OK));

    }

    @Test
    void testGetRepresentativeList() throws URISyntaxException, IOException {
        RepresentativeDTO representativeDTO = new RepresentativeDTO("a","a","a","B","a","a");
        ListRepresentativeOut r = representativeService.getRepresentative(context, representativeDTO);
        assertNotNull(r);
    }

}
