package dev.peytob.rpg.client.module.network.model;

public record ServerAuth(
    ServerConnectionDetails serverConnection,
    String token
) {
}
