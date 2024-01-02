package dev.peytob.rpg.backend.mapper;

import dev.peytob.rpg.backend.dto.user.CharacterGetDto;
import dev.peytob.rpg.backend.entity.CharacterEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(injectionStrategy = CONSTRUCTOR, componentModel = SPRING)
public interface CharacterMapper {

    @Mapping(target = "userId", source = "user.id")
    CharacterGetDto toCharacterDto(CharacterEntity characterEntity);

    Collection<CharacterGetDto> toCharacterDto(Collection<CharacterEntity> characterEntities);
}
