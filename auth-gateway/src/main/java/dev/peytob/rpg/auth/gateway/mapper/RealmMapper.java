package dev.peytob.rpg.auth.gateway.mapper;

import dev.peytob.rpg.auth.gateway.dto.RealmDto;
import dev.peytob.rpg.auth.gateway.entity.Realm;
import org.mapstruct.Mapper;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(injectionStrategy = CONSTRUCTOR, componentModel = SPRING)
public interface RealmMapper {

    RealmDto toRealmDto(Realm realm);
}
