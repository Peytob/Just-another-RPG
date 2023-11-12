package dev.peytob.rpg.backend.service;

import dev.peytob.rpg.backend.dto.auth.TokenDto;

import java.util.Optional;

public interface AuthProvider {

    Optional<String> login(String username, String password);

    void logout(String token);

    Optional<TokenDto> validate(String token);

    String register(String username, String password, String email);
}
