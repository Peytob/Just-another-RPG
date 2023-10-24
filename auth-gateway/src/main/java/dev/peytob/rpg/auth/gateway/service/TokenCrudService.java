package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.entity.Token;

import java.util.Optional;

public interface TokenCrudService {

    Token saveToken(Token token);

    Optional<Token> findTokenByHash(String tokenHash);

    void deleteToken(Token token);
}
