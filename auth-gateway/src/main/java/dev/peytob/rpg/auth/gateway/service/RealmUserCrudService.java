package dev.peytob.rpg.auth.gateway.service;

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

    void deleteUser(User user, Realm realm);

    User saveUser(User user, Realm realm);

    boolean isUserExistsByUsername(String username, Realm realm);

    boolean isUserExistsByEmail(String email, Realm realm);
}
