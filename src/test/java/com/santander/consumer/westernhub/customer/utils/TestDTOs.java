package com.santander.consumer.westernhub.customer.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class TestDTOs.
 */
public class TestDTOs {

    /**
     * The Class ExampleCountry.
     */
    @Getter
    @Setter
    public static class ExampleCountry {

        private String name;
        private String lastName;
        private String country;
        private String age;
    }

    /**
     * The Class ExampleLanguage.
     */
    @Getter
    @Setter
    public static class ExampleLanguage {

        private String id;
        private String lang;
    }
}


