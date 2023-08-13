package dev.peytob.rpg.client.module.network.service;

import dev.peytob.rpg.client.module.network.model.User;

import java.util.concurrent.CompletableFuture;

public interface ServerAuthService {

    CompletableFuture<User> login(String token);

    CompletableFuture<Void> logout(String token);
}
