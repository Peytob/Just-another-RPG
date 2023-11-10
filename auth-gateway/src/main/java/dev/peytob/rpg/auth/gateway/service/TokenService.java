package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.entity.Token;
import dev.peytob.rpg.auth.gateway.entity.TokenType;
import dev.peytob.rpg.auth.gateway.entity.User;

import java.util.Optional;

/**
 * Provides high-level operations on tokens.
 */
public interface TokenService {

    String createUserToken(User user, TokenType tokenType);

    Optional<Token> getTokenByValue(String tokenValue);

    void removeToken(Token token);
}
