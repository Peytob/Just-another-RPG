package dev.peytob.rpg.auth.gateway.repository;

import dev.peytob.rpg.auth.gateway.entity.Token;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends BaseRepository<Token> {
    Optional<Token> findByHash(String tokenHash);
}
