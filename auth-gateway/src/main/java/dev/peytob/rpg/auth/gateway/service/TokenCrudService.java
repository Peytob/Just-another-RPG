package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.entity.Token;

public interface TokenCrudService {

    Token saveToken(Token token);
}
