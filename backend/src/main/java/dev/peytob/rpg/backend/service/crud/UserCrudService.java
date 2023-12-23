package dev.peytob.rpg.backend.service.crud;

import dev.peytob.rpg.backend.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserCrudService {

    Optional<UserEntity> findUserById(String userId);

    UserEntity getUserById(String userId);

    Optional<UserEntity> findUserByUsername(String username);

    Page<UserEntity> getUsersPage(Pageable pageable);

    void deleteUser(UserEntity user);

    UserEntity saveUser(UserEntity user);

    boolean isUserExistsByUsername(String username);

    boolean isUserExistsByEmail(String email);
}
