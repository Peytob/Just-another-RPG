package dev.peytob.rpg.auth.gateway.repository;

import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {

    Optional<User> findByUsernameAndPasswordHashAndRealm(String username, String hashedPassword, Realm realm);

    Page<User> findAllByRealm(Realm realm, Pageable pageable);

    Optional<User> findByIdAndRealm(String userId, Realm realm);

    boolean existsByUsernameAndRealm(String username, Realm realm);

    boolean existsByEmailAndRealm(String email, Realm realm);
}
