package com.santander.consumer.westernhub.customer.service.clients;

import static org.junit.jupiter.api.Assertions.*;

import com.santander.ademma.common.model.dto.context.ExecutionContext;
import com.santander.consumer.westernhub.customer.model.dto.in.RepresentativeDTO;
import com.santander.consumer.westernhub.customer.model.dto.out.ListRepresentativeOut;
import com.santander.consumer.westernhub.customer.model.dto.out.error.ErrorMessageDTO;
import com.santander.consumer.westernhub.customer.service.utils.ServicesUtils;
import com.santander.ademma.common.exception.MappingException;

import com.santander.consumer.westernhub.customer.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


@ExtendWith(MockitoExtension.class)
class ServicesUtilsTest {

    @InjectMocks
    private ServicesUtils servicesUtils;

    @Mock
    private ExecutionContext context;


    @Test
    void testIsJsonValidValidJson() throws IOException {
        var validJson = TestUtils.fileToJson("mocks/endpoints/RepresentativeDTO-Mockeado.json");
        assertTrue(servicesUtils.isJsonValid(validJson));
    }

    @Test
    void testIsJsonValidInvalidJson() {
        String invalidJson = "invalid json";
        assertFalse(servicesUtils.isJsonValid(invalidJson));
    }

    @Test
    void testCheckEmptyErrors() {
        ErrorMessageDTO error1 = new ErrorMessageDTO("code1", "description", "","msg");
        ErrorMessageDTO error2 = new ErrorMessageDTO("", "", "","");
        ErrorMessageDTO error3 = new ErrorMessageDTO("code3", "description", "","msg");

        List<ErrorMessageDTO> errors = List.of(error1, error2, error3);
        List<ErrorMessageDTO> result = servicesUtils.checkEmptyErrors(errors);

        assertEquals(2, result.size());
        assertTrue(result.contains(error1));
        assertTrue(result.contains(error3));
    }

    @Test
    void testMappingException() throws IOException {
        var invalidJson = "invalid JSON string";

        assertThrows(MappingException.class,
                () -> servicesUtils.getObj(invalidJson, RepresentativeDTO.class));

    }


    @Test
    void testSwitchStatus() {
        String json = "{}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(json, HttpStatus.OK);

        ListRepresentativeOut result = servicesUtils.switchStatusRepresentatives(context, responseEntity);

        assertNotNull(result);
        assertNull(result.getErrors());
    }

    @Test
    void testSwitchStatusWithHttpStatus204() {
        ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);

        ListRepresentativeOut result = servicesUtils.switchStatusRepresentatives(context, responseEntity);

        assertNull(result);
    }

    @Test
    void testSwitchStatusWithHttpStatus500() throws IOException {
        var json = TestUtils.fileToJson("mocks/endpoints/RepresentativeDTO-Mockeado.json");;
        ResponseEntity<String> responseEntity = new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);

        ListRepresentativeOut result = servicesUtils.switchStatusRepresentatives(context, responseEntity);

        assertNotNull(result);

    }


}

