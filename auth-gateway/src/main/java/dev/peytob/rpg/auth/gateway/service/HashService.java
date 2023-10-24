package dev.peytob.rpg.auth.gateway.service;

public interface HashService {

    String hashPasswordString(String password);

    String hashTokenString(String token);
}
