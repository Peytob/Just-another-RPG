package dev.peytob.rpg.server.network.dto;

import dev.peytob.rpg.server.network.rpc.security.UserRole;

import java.time.Instant;
import java.util.Collection;

public record TokenDto(
    String username,
    Collection<UserRole> roles,
    Instant tokenExpiredAt
) {
}