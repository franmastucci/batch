package com.santander.consumer.westernhub.customer.config.docs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class can overwrite the inferred url of the servers
 * that appears in the dropdown bar of Swagger UI, in order to add the "path"
 * indicated in the values.yaml to the host: riskv.sceu.xxx.corp
 */

@Configuration
public class SwaggerHostConfig {

    /** The host uri. */
    @Value("${westernhub.host.path}")
    String hostUri;

    /** The version. */
    @Value("${info.app.version}")
    String version;

    @Bean
    public OpenAPI searchAPI() {
        final var securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addServersItem(new Server().url(hostUri))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .info(new Info()
                        .title("Operations Genesys")
                        .version(version)
                        .description("API to search data relative to loans")
                        .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0.html"))
                );
    }



}