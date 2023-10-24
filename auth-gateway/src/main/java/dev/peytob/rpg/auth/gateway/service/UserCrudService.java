package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.entity.User;

import java.util.Optional;

public interface UserCrudService {

    Optional<User> findByCredentials(String username, String passwordHash, Realm realm);
}
