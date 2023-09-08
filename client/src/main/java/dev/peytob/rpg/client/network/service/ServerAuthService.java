package dev.peytob.rpg.client.network.service;

import dev.peytob.rpg.client.network.model.User;

import java.util.concurrent.CompletableFuture;

public interface ServerAuthService {

    CompletableFuture<User> login();

    CompletableFuture<Void> logout();


}
