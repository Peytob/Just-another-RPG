package dev.peytob.rpg.client.network.model;

public record ServerDetails(
    String host,
    int port,
    NetworkScheme networkScheme
) {
}
