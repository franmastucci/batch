package com.santander.consumer.westernhub.customer.model.dto.out;

import jakarta.validation.constraints.NotNull;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

/**
 * The Class RepresentativeTest.
 *
 */
public class RepresentativeTest {

    /**
     * Test verify Representative.
     *
     */


    @Test
    public void verifyEqualsAndHashCodeOperationOut() {
        EqualsVerifier.forClass(Representative.class).withIgnoredAnnotations(NotNull.class)
                .suppress(Warning.STRICT_INHERITANCE).suppress(Warning.NONFINAL_FIELDS, Warning.BIGDECIMAL_EQUALITY).verify();
    }



}

