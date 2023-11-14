package dev.peytob.rpg.backend.repository;

import dev.peytob.rpg.backend.entity.TokenEntity;
import dev.peytob.rpg.backend.entity.TokenType;
import dev.peytob.rpg.backend.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends BaseRepository<TokenEntity> {

    void deleteAllByUser(UserEntity userEntity);

    Optional<TokenEntity> findByEncodedToken(String encodedToken);

    Optional<TokenEntity> findByUserAndType(UserEntity user, TokenType tokenType);
}
