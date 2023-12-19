package dev.peytob.rpg.auth.gateway.service;

/**
 * Contains set of sensitive hashing strategies. All passwords and tokens should be invalidated, if service realisation changed.
 */
public interface HashService {

    String hashPasswordString(String password);

    String hashTokenString(String token);
}
