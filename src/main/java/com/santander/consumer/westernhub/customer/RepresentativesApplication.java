package com.santander.consumer.westernhub.customer;

import com.santander.ademma.common.utils.logging.LoggingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

/**
 * OrganizationTables API executor.
 */
@EnableCaching
@SpringBootApplication(scanBasePackages = {"com.santander.consumer.westernhub.customer"}, exclude = {DataSourceAutoConfiguration.class})
@Slf4j
public class RepresentativesApplication {

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(RepresentativesApplication.class, args);
    }

    /**
     * Log environment variables.
     */
    @Bean
    public void logEnvironmentVariables() {
        LoggingUtils.logEnvironmentVariables(log);
    }

}
