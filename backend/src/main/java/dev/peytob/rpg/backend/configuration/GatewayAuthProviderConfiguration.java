package dev.peytob.rpg.backend.configuration;

import dev.peytob.rpg.backend.service.security.AuthGatewayAuthProvider;
import dev.peytob.rpg.backend.service.security.AuthProvider;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import static dev.peytob.rpg.backend.service.security.AuthGatewayAuthProvider.AUTH_GATEWAY_AUTHORIZATION_HEADER;

@Configuration
@ConditionalOnProperty(prefix = "auth", name = "type", havingValue = "auth-gateway")
@EnableConfigurationProperties(GatewayAuthProviderConfiguration.AuthGatewayProperties.class)
@Slf4j
@Deprecated
public class GatewayAuthProviderConfiguration {

    @Bean(name = "authGatewayRestTemplate")
    RestTemplate authGatewayRestTemplate(AuthGatewayProperties authGatewayProperties) {
        return new RestTemplateBuilder()
            .rootUri(authGatewayProperties.address() + authGatewayProperties.realmName() + "/")
            .defaultHeader(AUTH_GATEWAY_AUTHORIZATION_HEADER, authGatewayProperties.token())
            .build();
    }

    @Bean
    AuthProvider authGatewayAuthProvider(@Qualifier("authGatewayRestTemplate") RestTemplate restTemplate) {
        return new AuthGatewayAuthProvider(restTemplate);
    }

    @PostConstruct
    void logAuthService() {
        log.info("Initialized with 'Auth-Gateway' auth type.");
    }

    @ConfigurationProperties(prefix = "auth.auth-gateway")
    @Validated
    @Deprecated
    record AuthGatewayProperties(
        @URL
        String address,

        @NotBlank
        String token,

        @NotBlank
        String realmName
    ) {}
}
