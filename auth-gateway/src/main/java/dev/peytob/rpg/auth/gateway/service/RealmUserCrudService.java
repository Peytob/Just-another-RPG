package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.dto.user.UserCreateDto;
import dev.peytob.rpg.auth.gateway.dto.user.UserUpdateDto;
import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * User service, that adds a Realm check when performing crud operations, which allows them to be
 * carried out exclusively within specified Realm.
 */
public interface RealmUserCrudService {

    Optional<User> findUserByCredentials(String username, String passwordHash, Realm realm);

    Page<User> getUsersPage(Pageable pageable, Realm realm);

    Optional<User> findUserById(String userId, Realm realm);

    User getUserById(String userId, Realm realm);

    User createUser(UserCreateDto userCreateDto, Realm realm);

    User updateUser(User user, UserUpdateDto userUpdateDto, Realm realm);

    void deleteUser(User user, Realm realm);
}
