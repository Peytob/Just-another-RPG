package dev.peytob.rpg.auth.gateway.repository;

import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {

    Optional<User> findByUsernameAndPasswordHashAndRealm(String username, String hashedPassword, Realm realm);
}
