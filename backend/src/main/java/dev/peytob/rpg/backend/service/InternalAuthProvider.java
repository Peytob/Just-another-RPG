package dev.peytob.rpg.backend.service;

import dev.peytob.rpg.backend.dto.auth.TokenDto;
import dev.peytob.rpg.backend.entity.UserEntity;
import dev.peytob.rpg.backend.exception.LoginFailed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Component
public class InternalAuthProvider implements AuthProvider {

    private final UserService userService;

    @Override
    @Transactional
    public Optional<String> login(String username, String password) {
        UserEntity user = userService.getUserByCredentials(username, password).orElseThrow(() -> {
            log.info("User with username '{}' and password **** not found", username);
            return new LoginFailed();
        });

        if (user.isBlocked()) {
            log.info("Deactivated user with username '{}' is trying to login", username);
            throw new LoginFailed();
        }

        user.setLastLoginAt(Instant.now());

        return "mock_token".describeConstable();
    }

    @Override
    public void logout(String token) {

    }

    @Override
    public Optional<TokenDto> validate(String token) {
        return Optional.empty();
    }

    @Override
    public String register(String username, String password, String email) {
        userService.createUser(username, password, email);
        return "mock_token";
    }
}
