package dev.peytob.rpg.auth.gateway.mapper;

import dev.peytob.rpg.auth.gateway.dto.realm.RealmGetDto;
import dev.peytob.rpg.auth.gateway.entity.Realm;
import org.mapstruct.Mapper;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(injectionStrategy = CONSTRUCTOR, componentModel = SPRING)
public interface RealmMapper {

    RealmGetDto toRealmDto(Realm realm);
}
