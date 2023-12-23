package dev.peytob.rpg.backend.service.crud;

import dev.peytob.rpg.backend.entity.CharacterEntity;
import dev.peytob.rpg.backend.entity.UserEntity;

import java.util.Collection;

public interface CharacterCrudService {

    CharacterEntity saveCharacter(CharacterEntity characterEntity);

    boolean existsByName(String characterName);

    Collection<CharacterEntity> getByUser(UserEntity user);
}
