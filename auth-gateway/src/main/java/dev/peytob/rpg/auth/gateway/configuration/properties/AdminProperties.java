package dev.peytob.rpg.auth.gateway.configuration.properties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "admin")
@Validated
public record AdminProperties(
    @NotBlank
    String username,
    @NotBlank
    @Size(min = 16, message = "Token length should be at least 16 symbols")
    String token
) {
}
