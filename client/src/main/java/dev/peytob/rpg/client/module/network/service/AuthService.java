package dev.peytob.rpg.client.module.network.service;

import java.util.concurrent.Future;

public interface AuthService {

    Future<String> login(String username, String password);

    Future<Void> logout(String token);
}
