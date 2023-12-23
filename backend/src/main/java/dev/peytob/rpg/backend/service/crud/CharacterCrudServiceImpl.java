package dev.peytob.rpg.backend.service.crud;

import dev.peytob.rpg.backend.entity.CharacterEntity;
import dev.peytob.rpg.backend.entity.UserEntity;
import dev.peytob.rpg.backend.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CharacterCrudServiceImpl implements CharacterCrudService {

    private final CharacterRepository characterRepository;

    @Override
    @Transactional
    public CharacterEntity saveCharacter(CharacterEntity characterEntity) {
        return characterRepository.save(characterEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String characterName) {
        return characterRepository.existsByName(characterName);
    }

    @Override
    public Collection<CharacterEntity> getByUser(UserEntity user) {
        return characterRepository.findAllByUser(user);
    }
}
