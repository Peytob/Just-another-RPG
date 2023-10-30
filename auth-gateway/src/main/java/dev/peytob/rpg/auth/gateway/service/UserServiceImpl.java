package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RealmUserCrudService realmUserCrudService;

    private final HashService hashService;

    @Override
    public Optional<User> getUserByCredentials(String username, String password, Realm realm) {
        String hashedPassword = hashService.hashPasswordString(password);
        return realmUserCrudService.findUserByCredentials(username, hashedPassword, realm);
    }
}
