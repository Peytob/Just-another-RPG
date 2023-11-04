package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.entity.Token;

import java.util.Optional;

/**
 * Contains logic to perform directly user authorization.
 * Because this service should be used by user directly, this can work only with user raw token value.
 */
public interface LoginService {

    String loginUser(String username, String password, Realm realm);

    void logout(String tokenValue, Realm realm);

    Optional<Token> validateToken(String tokenValue, Realm realm);

    String registerUser(String username, String password, String email, Realm realm);
}
