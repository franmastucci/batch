package com.santander.consumer.westernhub.customer.model.dto.in;

import jakarta.validation.constraints.NotNull;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

/**
 * The Class RepresentativeDTOTest.
 *
 */
public class RepresentativeDTOTest {

    /**
     * Test verify representativeDTO.
     *
     */

    @Test
    public void verifyEqualsAndHashCodeLoanInDTO() {
        EqualsVerifier.forClass(RepresentativeDTO.class).withIgnoredAnnotations(NotNull.class)
                .suppress(Warning.STRICT_INHERITANCE).suppress(Warning.NONFINAL_FIELDS, Warning.BIGDECIMAL_EQUALITY).verify();
    }

}
