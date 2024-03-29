package dev.peytob.rpg.auth.gateway.repository;

import dev.peytob.rpg.auth.gateway.entity.Token;
import dev.peytob.rpg.auth.gateway.entity.User;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface TokenRepository extends BaseRepository<Token> {
    Optional<Token> findByHash(String tokenHash);

    void deleteAllByUser(User user);

    Collection<Token> findByExpirationAtAfter(Instant timepoint);
}
