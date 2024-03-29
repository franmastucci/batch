package com.santander.consumer.westernhub.customer;

import com.santander.ademma.common.utils.logging.LoggingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * OrganizationTables API executor.
 */
@EnableCaching
@EnableJpaRepositories(basePackages = "com.santander.consumer.westernhub.customer.repository")
@EntityScan(basePackages = "com.santander.consumer.westernhub.customer.model")
@SpringBootApplication(scanBasePackages = {"com.santander.consumer.westernhub.customer", "com.santander.ademma.common"})
@Slf4j
public class CodificationDataApplication {

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(CodificationDataApplication.class, args);
    }

    /**
     * Log environment variables.
     */
    @Bean
    public void logEnvironmentVariables() {
        LoggingUtils.logEnvironmentVariables(log);
    }

}
