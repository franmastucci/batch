package com.santander.consumer.westernhub.customer.config.httpclient;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.ConnectionKeepAliveStrategy;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.message.BasicHeaderElementIterator;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * Apache HttpClient configuration.
 */
@Configuration
@ConfigurationProperties("westernhub.customer.httpclient")
@EnableScheduling
@Getter
@Setter
@Slf4j
public class ApacheHttpClientConfig {

    // Connection pool
    private int maxRouteConnections;
    private int maxTotalConnections;

    // Keep alive
    private int defaultKeepAliveTime;

    // Timeouts
    private int connectionTimeout; // the time for waiting until a connection is established
    private int requestTimeout; // the time for waiting for a connection from connection pool
    private int socketTimeout; // the time for waiting for data

    // Idle connection monitor
    private int idleConnectionWaitTime;

    /**
     * Connection pool configuration.
     *
     * @return PoolingHttpClientConnectionManager
     */
    @Bean
    @Qualifier("poolingConnectionManager")
    public PoolingHttpClientConnectionManager poolingConnectionManager() {
        var poolingConnectionManager = new PoolingHttpClientConnectionManager();

        var connectionConfig = ConnectionConfig.custom()
                .setConnectTimeout(connectionTimeout, TimeUnit.MILLISECONDS).build();

        poolingConnectionManager.setDefaultConnectionConfig(connectionConfig);

        // set total amount of connections across all HTTP routes
        poolingConnectionManager.setMaxTotal(maxTotalConnections);

        // set maximum amount of connections for each http route in pool
        poolingConnectionManager.setDefaultMaxPerRoute(maxRouteConnections);

        return poolingConnectionManager;
    }

    /**
     * Connection keep alive configuration.
     *
     * @return ConnectionKeepAliveStrategy
     */
    @Bean
    @Qualifier("connectionKeepAliveStrategy")
    public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
        return (httpResponse, httpContext) -> {
            var headerIterator = httpResponse.headerIterator(HttpHeaders.KEEP_ALIVE);
            var elementIterator = new BasicHeaderElementIterator(headerIterator);

            while (elementIterator.hasNext()) {
                var element = elementIterator.next();
                var param = element.getName();
                var value = element.getValue();
                if (value != null && param.equalsIgnoreCase("timeout")) {
                    return TimeValue.of(Long.parseLong(value), TimeUnit.SECONDS);
                }
            }

            return TimeValue.of(defaultKeepAliveTime, TimeUnit.MILLISECONDS);
        };
    }

    /**
     * Monitor which closes expired connections.
     *
     * @param pool pool manager
     * @return Runnable
     */
    @Bean
    @Qualifier("idleConnectionMonitor")
    public Runnable idleConnectionMonitor(@Autowired @Qualifier("poolingConnectionManager") PoolingHttpClientConnectionManager pool) {
        return new Runnable() {
            @Override
            @Scheduled(fixedDelay = 300000)
            public void run() {
                // only if connection pool is initialised
                if (pool != null) {
                    pool.closeExpired();
                    pool.closeIdle(TimeValue.of(idleConnectionWaitTime, TimeUnit.MILLISECONDS));

                    log.debug("Idle connection monitor: Closing expired and idle connections");
                }
            }
        };
    }

    /**
     * Task to execute monitor
     *
     * @return TaskScheduler
     */
    @Bean
    @Qualifier("taskScheduler")
    public TaskScheduler taskScheduler() {
        var scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("idleMonitor");
        scheduler.setPoolSize(5);
        return scheduler;
    }

    /**
     * Creates a instance of HttpClient
     *
     * @return CloseableHttpClient
     */
    @Bean
    @Qualifier("httpClient")
    public CloseableHttpClient httpClient() {
        var requestConfig = RequestConfig.custom().setConnectionRequestTimeout(Timeout.ofMilliseconds(requestTimeout))
                .setResponseTimeout(Timeout.ofMilliseconds(socketTimeout)).build();

        return HttpClients.custom().setDefaultRequestConfig(requestConfig)
                .setConnectionManager(poolingConnectionManager()).setKeepAliveStrategy(connectionKeepAliveStrategy())
                .build();
    }

    @Bean
    @Qualifier("httpClientSSL")
    public CloseableHttpClient httpClientSSL() {
        try {
            var requestConfig = RequestConfig.custom().setConnectionRequestTimeout(Timeout.ofMilliseconds(connectionTimeout))
                    .setResponseTimeout(Timeout.ofMilliseconds(socketTimeout)).build();

            var connectionConfig = ConnectionConfig.custom()
                    .setConnectTimeout(connectionTimeout, TimeUnit.MILLISECONDS)
                    .setSocketTimeout(socketTimeout, TimeUnit.MILLISECONDS)
                    .build();

            TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

            var sslContext = SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy).build();

            var socketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

            HttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create().setSSLSocketFactory(socketFactory)
                    .setDefaultConnectionConfig(connectionConfig).build();

            return HttpClients.custom().setDefaultRequestConfig(requestConfig).setConnectionManager(connectionManager).build();

        } catch (KeyStoreException | NoSuchAlgorithmException | KeyManagementException exc) {
            return null;
        }
    }

}