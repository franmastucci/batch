package com.santander.consumer.westernhub.customer.model.dto.out;

import jakarta.validation.constraints.NotNull;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

/**
 * The Class ListRepresentativeOutTest.
 *
 */
public class ListRepresentativeOutTest {

    /**
     * Test verify ListRepresentativeOut.
     *
     */


    @Test
    public void verifyEqualsAndHashCodeOperationOut() {
        EqualsVerifier.forClass(ListRepresentativeOut.class).withIgnoredAnnotations(NotNull.class)
                .suppress(Warning.STRICT_INHERITANCE).suppress(Warning.NONFINAL_FIELDS, Warning.BIGDECIMAL_EQUALITY).verify();
    }



}

