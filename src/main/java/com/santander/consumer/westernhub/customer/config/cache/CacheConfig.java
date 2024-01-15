package com.santander.consumer.westernhub.customer.config.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;

/**
 * The Class CacheConfig.
 */
@Configuration
@EnableCaching
@ConfigurationProperties("westernhub.customer.cache")
@Getter
@Setter
@Slf4j
public class CacheConfig {

    /** The expire. */
    private long expire;

    /**
     * Cache manager.
     *
     * @return the cache manager
     */
    @Bean
    @Primary
    public CacheManager cacheManager() {
        var cacheManager = new CaffeineCacheManager("pagination");
        cacheManager.setCaffeine(caffeineCacheBuilder());
        log.info("Caches created: {}", cacheManager.getCacheNames());
        return cacheManager;
    }

    /**
     * Caffeine cache builder.
     *
     * @return the caffeine
     */
    @NonNull
    Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder().expireAfterAccess(Duration.ofSeconds(expire))
                .expireAfterWrite(Duration.ofSeconds(expire)).recordStats();
    }

}