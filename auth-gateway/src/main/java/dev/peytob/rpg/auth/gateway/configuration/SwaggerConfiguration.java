package dev.peytob.rpg.auth.gateway.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Auth-gateway API", version = "v1"))
public class SwaggerConfiguration {

    @Bean
    GroupedOpenApi restApi() {
        return GroupedOpenApi.builder()
            .group("public")
            .displayName("Authentication")
            .pathsToMatch("/auth/**")
            .build();
    }
}
