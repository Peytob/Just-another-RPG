package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.entity.User;

import java.util.Optional;

/**
 * Provides high-level operations on users.
 */
public interface UserService {

    Optional<User> getUserByCredentials(String username, String password, Realm realm);
}
