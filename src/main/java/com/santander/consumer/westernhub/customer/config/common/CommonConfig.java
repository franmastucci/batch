package com.santander.consumer.westernhub.customer.config.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * The Class CommonConfig.
 */
@Configuration
@ComponentScan("com.santander.ademma.common")
@Getter
@Setter
public class CommonConfig {

    /** The Constant ACTIVE. */
    public static final String ACTIVE = "A";

    /** The Constant INACTIVE. */
    public static final String INACTIVE = "I";

    /** The Constant OK. */
    public static final String OK = "OK";

    /** The Constant KO. */
    public static final String KO = "KO";

    /**
     * The Class Types.
     */
    public class Types {

        /** The Constant ERROR. */
        public static final String ERROR = "ERROR";

        /** The Constant WARNING. */
        public static final String WARNING = "WARNING";

        /** The Constant INFO. */
        public static final String INFO = "INFO";
        
        /** The Constant FATAL. */
        public static final String FATAL = "FATAL";
    }

}
