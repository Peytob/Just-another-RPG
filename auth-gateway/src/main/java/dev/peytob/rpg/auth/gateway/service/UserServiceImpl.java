package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.dto.user.UserCreateDto;
import dev.peytob.rpg.auth.gateway.dto.user.UserUpdateDto;
import dev.peytob.rpg.auth.gateway.entity.Group;
import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RealmUserCrudService realmUserCrudService;

    private final RealmGroupCrudService realmGroupCrudService;

    private final HashService hashService;

    @Override
    public Optional<User> getUserByCredentials(String username, String password, Realm realm) {
        String hashedPassword = hashService.hashPasswordString(password);
        return realmUserCrudService.findUserByCredentials(username, hashedPassword, realm);
    }

    @Override
    public User updateUser(User user, UserUpdateDto userUpdateDto, Realm realm) {
        Collection<Group> groups = realmGroupCrudService.getGroupsByIds(userUpdateDto.groups(), realm);

        String hashedPassword = hashService.hashPasswordString(userUpdateDto.password());

        user.setEmail(userUpdateDto.email());
        user.setUsername(userUpdateDto.username());
        user.setPasswordHash(hashedPassword);

        user.getGroups().clear();
        user.getGroups().addAll(groups);

        return realmUserCrudService.saveUser(user, realm);
    }

    @Override
    public User createUser(UserCreateDto userCreateDto, Realm realm) {
        Collection<Group> groups = realmGroupCrudService.getGroupsByIds(userCreateDto.groups(), realm);

        String hashedPassword = hashService.hashPasswordString(userCreateDto.password());

        User user = User.builder()
            .email(userCreateDto.email())
            .passwordHash(hashedPassword)
            .isBlocked(false)
            .realm(realm)
            .groups(groups)
            .username(userCreateDto.username())
            .build();

        return realmUserCrudService.saveUser(user, realm);
    }

    @Override
    public void deleteUser(User user, Realm realm) {
        realmUserCrudService.deleteUser(user, realm);
    }
}
