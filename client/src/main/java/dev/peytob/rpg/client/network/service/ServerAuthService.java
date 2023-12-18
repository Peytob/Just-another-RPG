package dev.peytob.rpg.client.network.service;

import dev.peytob.rpg.client.network.model.User;
import io.grpc.Channel;

import java.util.concurrent.CompletableFuture;

public interface ServerAuthService {

    CompletableFuture<User> login(String username, String password, Channel channel);

    CompletableFuture<Void> logout();

}
