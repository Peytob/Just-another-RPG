package dev.peytob.rpg.server.network.dto;

public record WorldContextEnterDto(
    String characterId,
    String worldContextId
) {
}
