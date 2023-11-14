package dev.peytob.rpg.backend.service;

import dev.peytob.rpg.backend.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public Optional<UserEntity> getUserByCredentials(String username, String password) {
        return Optional.empty();
    }

    @Override
    public UserEntity createUser(String username, String password, String email) {
        return null;
    }
}
