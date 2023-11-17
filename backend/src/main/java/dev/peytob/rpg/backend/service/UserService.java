package dev.peytob.rpg.backend.service;

import dev.peytob.rpg.backend.entity.UserEntity;

import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findUserByCredentials(String username, String password);

    UserEntity createPlayerUser(String username, String password, String email);

}
