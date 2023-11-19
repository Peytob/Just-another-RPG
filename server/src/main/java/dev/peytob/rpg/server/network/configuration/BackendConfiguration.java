package dev.peytob.rpg.server.network.configuration;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(BackendConfiguration.BackendProperties.class)
public class BackendConfiguration {

    @Bean
    RestTemplate backendRestTemplate(BackendProperties backendProperties) {
        return new RestTemplateBuilder()
            .rootUri(backendProperties.address())
            .defaultHeader("Authorization", backendProperties.token())
            .build();
    }

    @ConfigurationProperties(prefix = "rpg.backend")
    @Validated
    record BackendProperties(
        @URL
        String address,

        @NotBlank
        String token
    ) {}
}
