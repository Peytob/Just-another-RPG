package dev.peytob.rpg.auth.gateway.mapper;

import dev.peytob.rpg.auth.gateway.dto.user.UserDto;
import dev.peytob.rpg.auth.gateway.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(injectionStrategy = CONSTRUCTOR, componentModel = SPRING)
public interface UserMapper {

    @Mapping(target = "isBlocked", expression = "java(user.isBlocked())")
    UserDto toUserDto(User user);
}
