package dev.peytob.rpg.backend.service;

import dev.peytob.rpg.backend.dto.auth.TokenDto;

import java.util.Optional;

public interface UserAuthService {

    Optional<String> loginUser(String username, String password);

    void logoutUser(String token);

    Optional<TokenDto> validateToken(String token);

    String registerUser(String username, String password, String email);
}
