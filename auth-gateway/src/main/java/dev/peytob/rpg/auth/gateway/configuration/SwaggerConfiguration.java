package dev.peytob.rpg.auth.gateway.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static dev.peytob.rpg.auth.gateway.configuration.SecurityConfiguration.AUTHORIZATION_HEADER;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Auth-gateway API", version = "v1"), security = @SecurityRequirement(name = "authorization_token_auth"))
@SecurityScheme(name = "authorization_token_auth", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER, paramName = AUTHORIZATION_HEADER)
public class SwaggerConfiguration {

    @Bean
    GroupedOpenApi publicAuth() {
        return GroupedOpenApi.builder()
            .group("public")
            .displayName("Public authentication")
            .pathsToMatch("/*/auth/**")
            .build();
    }

    @Bean
    GroupedOpenApi allAvailableApi() {
        return GroupedOpenApi.builder()
                .group("management")
                .displayName("All available API")
                .pathsToMatch("/**")
                .build();
    }
}
