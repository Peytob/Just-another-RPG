package dev.peytob.rpg.server.network.model;

public record LoginDto(
    String username,
    String password
) {
}
