package dev.peytob.rpg.auth.gateway.controller;

import dev.peytob.rpg.auth.gateway.dto.UserDto;
import dev.peytob.rpg.auth.gateway.dto.user.UserCreateDto;
import dev.peytob.rpg.auth.gateway.dto.user.UserUpdateDto;
import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.entity.User;
import dev.peytob.rpg.auth.gateway.mapper.UserMapper;
import dev.peytob.rpg.auth.gateway.service.RealmCrudService;
import dev.peytob.rpg.auth.gateway.service.RealmUserCrudService;
import dev.peytob.rpg.auth.gateway.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{realmId}/users")
@RequiredArgsConstructor
public class UserController {

    private final RealmCrudService realmCrudService;

    private final RealmUserCrudService realmUserCrudService;

    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping("/")
    Page<UserDto> getUsersPage(@PathVariable String realmId, @ParameterObject Pageable pageable) {
        Realm realm = realmCrudService.getRealmById(realmId);
        return realmUserCrudService.getUsersPage(pageable, realm).map(userMapper::toUserDto);
    }

    @GetMapping("/{userId}")
    UserDto getUser(@PathVariable String realmId, @PathVariable String userId) {
        Realm realm = realmCrudService.getRealmById(realmId);
        User user = realmUserCrudService.getUserById(userId, realm);
        return userMapper.toUserDto(user);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    UserDto createUser(@PathVariable String realmId, @Valid @RequestBody UserCreateDto userCreateDto) {
        Realm realm = realmCrudService.getRealmById(realmId);
        User user = userService.createUser(userCreateDto, realm);
        return userMapper.toUserDto(user);
    }

    @PutMapping("/{userId}")
    UserDto updateRealm(@PathVariable String realmId, @PathVariable String userId, @Valid @RequestBody UserUpdateDto userUpdateDto) {
        Realm realm = realmCrudService.getRealmById(realmId);
        User user = realmUserCrudService.getUserById(userId, realm);
        User updatedUser = userService.updateUser(user, userUpdateDto, realm);
        return userMapper.toUserDto(updatedUser);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@PathVariable String realmId, @PathVariable String groupId) {
        Realm realm = realmCrudService.getRealmById(realmId);
        User user = realmUserCrudService.getUserById(groupId, realm);
        userService.deleteUser(user, realm);
    }
}
