package dev.peytob.rpg.backend.service;

import dev.peytob.rpg.backend.entity.TokenEntity;
import dev.peytob.rpg.backend.entity.UserEntity;

import java.util.Optional;

public interface TokenService {

    String createUserSessionToken(UserEntity user);

    Optional<TokenEntity> getTokenByRawValue(String tokenValue);

    void removeToken(TokenEntity token);
}
