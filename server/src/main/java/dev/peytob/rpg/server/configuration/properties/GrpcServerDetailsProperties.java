package dev.peytob.rpg.server.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("rpg.server.details")
public record GrpcServerDetailsProperties(
    String address,
    Integer port,
    String name
) {
}
