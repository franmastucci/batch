package com.santander.consumer.westernhub.customer.config.security;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.resource.IssuerUriCondition;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

@Configuration(proxyBeanMethods = false)
public class JwtDecoderAutoConfiguration {

	private final OAuth2ResourceServerProperties.Jwt properties;

	JwtDecoderAutoConfiguration(OAuth2ResourceServerProperties properties) {
		this.properties = properties.getJwt();
	}

	@Bean
	@ConditionalOnProperty(name = "spring.security.oauth2.resourceserver.jwt.jwk-set-uri")
	JwtDecoder jwtDecoderByJwkKeySetUri(@Autowired @Qualifier("httpClientSSL") CloseableHttpClient httpClient)
			throws URISyntaxException {
		var jwtDecoderBuilder = NimbusJwtDecoder.withJwkSetUri(this.properties.getJwkSetUri())
				.jwsAlgorithm(SignatureAlgorithm.from(this.properties.getJwsAlgorithms().get(0)
				));

		if (isSslUri(this.properties.getJwkSetUri())) {
			jwtDecoderBuilder.restOperations(getTrustedRestOperations(httpClient));
		}

		var nimbusJwtDecoder = jwtDecoderBuilder.build();

		var issuerUri = this.properties.getIssuerUri();
		Supplier<OAuth2TokenValidator<Jwt>> defaultValidator = (issuerUri != null)
				? () -> JwtValidators.createDefaultWithIssuer(issuerUri)
				: JwtValidators::createDefault;
		nimbusJwtDecoder.setJwtValidator(getValidators(defaultValidator));
		return nimbusJwtDecoder;
	}

	@Bean
	@Conditional(IssuerUriCondition.class)
	SupplierJwtDecoder jwtDecoderByIssuerUri() {
		return new SupplierJwtDecoder(() -> {
			var issuerUri = this.properties.getIssuerUri();

			NimbusJwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation(issuerUri);

			jwtDecoder.setJwtValidator(getValidators(() -> JwtValidators.createDefaultWithIssuer(issuerUri)));
			return jwtDecoder;
		});
	}

	private boolean isSslUri(String jwkSetUri) throws URISyntaxException {
		var uri = new URI(jwkSetUri);
		return "https".equals(uri.getScheme());
	}

	OAuth2TokenValidator<Jwt> getValidators(Supplier<OAuth2TokenValidator<Jwt>> defaultValidator) {
		var defaultValidators = defaultValidator.get();
		var audiences = this.properties.getAudiences();
		if (CollectionUtils.isEmpty(audiences)) {
			return defaultValidators;
		}
		List<OAuth2TokenValidator<Jwt>> validators = new ArrayList<>();
		validators.add(defaultValidators);
		validators.add(new JwtClaimValidator<List<String>>(JwtClaimNames.AUD,
				(aud) -> aud != null && !Collections.disjoint(aud, audiences)));
		return new DelegatingOAuth2TokenValidator<>(validators);
	}

	private RestOperations getTrustedRestOperations(CloseableHttpClient httpClient) {
		var requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);

		return new RestTemplate(requestFactory);
	}
}
