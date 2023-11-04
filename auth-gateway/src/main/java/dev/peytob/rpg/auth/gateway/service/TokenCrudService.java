package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.entity.Token;

import java.util.Collection;
import java.util.Optional;

public interface TokenCrudService {

    Token saveToken(Token token);

    Optional<Token> findTokenByHash(String tokenHash);

    void deleteToken(Token token);

    Collection<Token> findExpiredTokens();

    void deleteTokens(Collection<Token> expiredTokens);
}
