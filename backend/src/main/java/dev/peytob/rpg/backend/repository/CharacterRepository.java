package dev.peytob.rpg.backend.repository;

import dev.peytob.rpg.backend.entity.CharacterEntity;
import dev.peytob.rpg.backend.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CharacterRepository extends BaseRepository<CharacterEntity> {

    boolean existsByName(String name);

    Collection<CharacterEntity> findAllByUser(UserEntity user);
}
