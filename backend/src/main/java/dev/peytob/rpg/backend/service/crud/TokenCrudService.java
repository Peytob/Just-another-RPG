package dev.peytob.rpg.backend.service.crud;

import dev.peytob.rpg.backend.entity.TokenEntity;
import dev.peytob.rpg.backend.entity.UserEntity;

import java.util.Optional;

public interface TokenCrudService {

    Optional<TokenEntity> findUserSessionToken(UserEntity user);

    TokenEntity saveToken(TokenEntity token);

    Optional<TokenEntity> findByEncodedToken(String encodedToken);

    void deleteToken(TokenEntity token);
}
