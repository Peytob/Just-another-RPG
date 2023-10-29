package dev.peytob.rpg.auth.gateway.mapper;

import dev.peytob.rpg.auth.gateway.dto.TokenInfoDto;
import dev.peytob.rpg.auth.gateway.entity.Group;
import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.entity.Token;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(injectionStrategy = CONSTRUCTOR, componentModel = SPRING)
public interface TokenMapper {

    @Mappings({
        @Mapping(target = "realmName", source = "realm.name"),
        @Mapping(target = "username", source = "token.user.username"),
        @Mapping(target = "email", source = "token.user.email"),
        @Mapping(target = "groups", source = "token.user.groups", qualifiedByName = "extractGroupName"),
        @Mapping(target = "tokenType", source = "token.type"),
        @Mapping(target = "tokenExpiredAt", source = "token.expirationAt")
    })
    TokenInfoDto toTokenInfoDto(Token token, Realm realm);

    @Named("extractGroupName")
    default String extractGroupName(Group group) {
        return group.getName();
    }
}
