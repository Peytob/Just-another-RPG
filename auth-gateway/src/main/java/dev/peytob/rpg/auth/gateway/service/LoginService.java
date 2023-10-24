package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.entity.TokenType;

public interface LoginService {

    String loginUser(String username, String password, TokenType tokenType, Realm realm);
}
