package com.santander.consumer.westernhub.customer.error;


import com.santander.consumer.westernhub.customer.model.dto.out.error.ErrorMessageDTO;
import jakarta.validation.constraints.NotNull;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

public class ErrorMessageDTOTest {

    @Test
    public void verifyEqualsAndHashCodeAS400ErrorMessageDTO() {
        EqualsVerifier.forClass(ErrorMessageDTO.class).withIgnoredAnnotations(NotNull.class)
                .suppress(Warning.STRICT_INHERITANCE).suppress(Warning.NONFINAL_FIELDS, Warning.BIGDECIMAL_EQUALITY).verify();
    }

}
