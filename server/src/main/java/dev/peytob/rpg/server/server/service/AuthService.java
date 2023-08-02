package dev.peytob.rpg.server.server.service;

public interface AuthService {

    void login(String token);

    void logout(String token);
}
