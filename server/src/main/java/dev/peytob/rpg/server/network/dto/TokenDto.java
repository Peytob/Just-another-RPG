package dev.peytob.rpg.server.network.dto;

import java.time.Instant;
import java.util.Collection;

public record TokenDto(
        String username,
        Collection<String> roles,
        Instant tokenExpiredAt
    ) {
    }