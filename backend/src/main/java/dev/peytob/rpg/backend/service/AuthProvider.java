package dev.peytob.rpg.backend.service;

import dev.peytob.rpg.backend.dto.auth.TokenDto;

import java.util.Optional;

public interface AuthProvider {

    Optional<String> loginUser(String username, String password);

    void logoutUser(String tokenValue);

    Optional<TokenDto> validateRawToken(String rawToken);

    String registerPlayerUser(String username, String password, String email);
}
