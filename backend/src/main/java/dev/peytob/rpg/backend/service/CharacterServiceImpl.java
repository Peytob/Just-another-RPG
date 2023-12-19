package dev.peytob.rpg.backend.service;

import dev.peytob.rpg.backend.entity.CharacterEntity;
import dev.peytob.rpg.backend.entity.UserEntity;
import dev.peytob.rpg.backend.exception.EntityAlreadyExistsException;
import dev.peytob.rpg.backend.service.crud.CharacterCrudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CharacterServiceImpl implements CharacterService {

    private final CharacterCrudService characterCrudService;

    @Override
    @Transactional
    public CharacterEntity createCharacter(String characterName, UserEntity user) {
        log.info("Creating new character with name {} for user {}", characterName, user.getId());

        if (characterCrudService.existsByName(characterName)) {
            throw new EntityAlreadyExistsException("Character with name " + characterName + " is already exists!");
        }

        CharacterEntity character = CharacterEntity.builder()
            .name(characterName)
            .user(user)
            .build();

        character = characterCrudService.saveCharacter(character);

        log.info("Created new character with id {} for user {}", character.getId(), user.getUsername());

        return character;
    }
}
