package dev.peytob.rpg.client.module.network.service;

import java.util.concurrent.CompletableFuture;

public interface AuthService {

    CompletableFuture<String> login(String username, String password);

    CompletableFuture<Void> logout(String token);
}
