package dev.peytob.rpg.auth.gateway.mapper;

import dev.peytob.rpg.auth.gateway.dto.UserDto;
import dev.peytob.rpg.auth.gateway.entity.User;
import org.mapstruct.Mapper;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(injectionStrategy = CONSTRUCTOR, componentModel = SPRING)
public interface UserMapper {

    UserDto toUserDto(User user);
}
