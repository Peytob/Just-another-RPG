package dev.peytob.rpg.client.module.network.model;

public record ServerConnectionDetails(
    String name,
    String address,
    Integer port
) {
}
