package dev.peytob.rpg.backend.service;

import dev.peytob.rpg.backend.entity.CharacterEntity;
import dev.peytob.rpg.backend.entity.UserEntity;

public interface CharacterService {

    CharacterEntity createCharacter(String characterName, UserEntity user);
}
