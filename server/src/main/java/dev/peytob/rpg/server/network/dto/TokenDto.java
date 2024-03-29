package dev.peytob.rpg.server.network.dto;

import dev.peytob.rpg.server.network.constant.UserRole;

import java.time.Instant;
import java.util.Collection;

public record TokenDto(
    String userId,
    String username,
    Collection<UserRole> roles,
    Instant tokenExpiredAt
) {
}