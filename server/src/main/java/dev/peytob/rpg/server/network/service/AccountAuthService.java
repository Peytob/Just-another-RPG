package dev.peytob.rpg.server.network.service;

import dev.peytob.rpg.server.network.dto.TokenDto;

import java.util.Optional;

/**
 * This service allow user to auth in backend.
 */
public interface AccountAuthService {

    String login(String username, String password);

    void logout(String token);

    Optional<TokenDto> validate(String token);
}
