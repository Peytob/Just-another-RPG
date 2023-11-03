package dev.peytob.rpg.auth.gateway.configuration.properties;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "auth")
@Validated
public record AuthProperties(
    @NotNull
    Boolean registrationByUserEnabled,
    @NotNull
    Boolean longTimeTokensEnabled
) {
}
