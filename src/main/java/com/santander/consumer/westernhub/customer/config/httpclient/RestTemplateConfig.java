package com.santander.consumer.westernhub.customer.config.httpclient;

import com.santander.consumer.westernhub.customer.handler.httpclient.RepresentativesClientErrorHandler;
import com.santander.consumer.westernhub.customer.handler.httpclient.RepresentativesClientHttpRequestInterceptor;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate configuration.
 *
 */
@Configuration
@ConfigurationProperties("westernhub.customer.httpclient")
public class RestTemplateConfig {

    /** The connection timeout. */
    // the time for waiting until a connection is established
    private int connectionTimeout;

    /** The http client. */
    private final CloseableHttpClient httpClient;

    /**
     * Constructor.
     *
     * @param httpClient httpClient
     */
    public RestTemplateConfig(@Autowired @Qualifier("httpClient") CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Creates HttpRequest factory.
     *
     * @return HttpComponentsClientHttpRequestFactory
     */
    @Bean
    @Qualifier("clientHttpRequestFactory")
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
        var clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClient);
        return clientHttpRequestFactory;
    }

    /**
     * Create a new RestTemplate.
     *
     * @param restTemplateBuilder RestTemplateBuilder
     * @return RestTemplate
     */
    @Bean
    @Qualifier("restTemplate")
    public RestTemplate restTemplate(@Autowired RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.requestFactory(this::clientHttpRequestFactory)
                .errorHandler(new RepresentativesClientErrorHandler())
                .interceptors(new RepresentativesClientHttpRequestInterceptor()).build();
    }

    /**
     * Create a new RestTemplate.
     *
     * @param httpClient the http client
     * @return RestTemplate
     */
    @Bean
    @Qualifier("restTemplateSSL")
    public RestTemplate restTemplateSSL(@Autowired @Qualifier("httpClientSSL") CloseableHttpClient httpClient) {

        var requestFactory = new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);
        // Connect timeout
        requestFactory.setConnectTimeout(connectionTimeout);

        requestFactory.setConnectionRequestTimeout(connectionTimeout);

        return new RestTemplate(requestFactory);

    }
}
