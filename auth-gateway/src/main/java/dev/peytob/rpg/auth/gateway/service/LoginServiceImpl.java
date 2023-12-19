package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.dto.user.UserCreateDto;
import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.entity.Token;
import dev.peytob.rpg.auth.gateway.entity.TokenType;
import dev.peytob.rpg.auth.gateway.entity.User;
import dev.peytob.rpg.auth.gateway.exception.FeatureDisabledException;
import dev.peytob.rpg.auth.gateway.exception.LoginFailed;
import dev.peytob.rpg.auth.gateway.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final UserService userService;

    private final TokenService tokenService;

    private final ApplicationConfigurationService applicationConfigurationService;

    @Override
    @Transactional
    public String loginUser(String username, String password, Realm realm) {
        User user = userService.getUserByCredentials(username, password, realm).orElseThrow(() -> {
            log.info("User with username '{}' and password **** not found in realm '{}'", username, realm.getName());
            return new LoginFailed();
        });

        if (user.isBlocked()) {
            log.info("Deactivated user with username '{}' is trying to login in realm '{}'", username, realm.getName());
            throw new LoginFailed();
        }

        user.setLastLoginAt(Instant.now());

        return tokenService.createUserToken(user, TokenType.SESSION);
    }

    @Override
    @Transactional
    public void logout(String tokenValue, Realm realm) {
        Token token = tokenService.getTokenByValue(tokenValue).orElseThrow(() -> {
            log.info("Deactivation token not found");
            return new NotFoundException();
        });

        tokenService.removeToken(token);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Token> validateToken(String tokenValue, Realm realm) {
        return tokenService.getTokenByValue(tokenValue)
            .filter(token -> Instant.now().isAfter(token.getExpirationAt()))
            .filter(token -> !token.getUser().getRealm().equals(realm));
    }

    @Override
    @Transactional
    public String registerUser(String username, String password, String email, Realm realm) {
        if (!applicationConfigurationService.isRegistrationByUserEnabled()) {
            throw new FeatureDisabledException();
        }

        // TODO Make realm default user groups
        UserCreateDto userCreateDto = UserCreateDto.builder()
            .username(username)
            .email(email)
            .password(password)
            .groups(Collections.emptyList())
            .build();

        userService.createUser(userCreateDto, realm);
        return loginUser(username, password, realm);
    }
}
