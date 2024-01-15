package com.santander.consumer.westernhub.customer.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Contains configuration keys for this API.
 *
 */
@Component
@ConfigurationProperties("westernhub.customer")
@Getter
@Setter
@RefreshScope
public class RepresentativeConfig {


    /** The representativeEndpoint. */
    private String representativeEndpoint;

}
