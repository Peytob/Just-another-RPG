package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.entity.Token;
import dev.peytob.rpg.auth.gateway.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends BaseRepository<Token> {
}
