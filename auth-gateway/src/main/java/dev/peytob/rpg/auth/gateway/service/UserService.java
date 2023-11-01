package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.dto.user.UserCreateDto;
import dev.peytob.rpg.auth.gateway.dto.user.UserUpdateDto;
import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.entity.User;

import java.util.Optional;

/**
 * Provides high-level operations on users.
 */
public interface UserService {

    Optional<User> getUserByCredentials(String username, String password, Realm realm);

    void deleteUser(User user, Realm realm);

    User updateUser(User user, UserUpdateDto userUpdateDto, Realm realm);

    User createUser(UserCreateDto userCreateDto, Realm realm);
}
