package dev.peytob.rpg.server.loader.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.nio.file.Path;

@ConfigurationProperties(prefix = "rpg.loader.file-structure")
@Validated
public record FileStructureProperties(
    @NotNull
    Path tiles,

    @NotNull
    Path tilemaps,

    @NotNull
    Path worlds
) {
}
