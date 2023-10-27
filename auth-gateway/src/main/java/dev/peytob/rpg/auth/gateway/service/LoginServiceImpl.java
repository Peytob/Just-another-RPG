package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.entity.Token;
import dev.peytob.rpg.auth.gateway.entity.TokenType;
import dev.peytob.rpg.auth.gateway.entity.User;
import dev.peytob.rpg.auth.gateway.exception.NotFoundException;
import dev.peytob.rpg.auth.gateway.exception.LoginFailed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final UserService userService;

    private final TokenService tokenService;

    @Override
    @Transactional
    public String loginUser(String username, String password, TokenType tokenType, Realm realm) {
        User user = userService.getUserByCredentials(username, password, realm).orElseThrow(() -> {
            log.info("User with username '{}' and password **** not found in realm '{}'", username, realm.getName());
            return new LoginFailed();
        });

        if (user.isBlocked()) {
            log.info("Deactivated user with username '{}' is trying to login in realm '{}'", username, realm.getName());
            throw new LoginFailed();
        }

        return tokenService.createUserToken(user, tokenType);
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
        Optional<Token> optionalToken = tokenService.getTokenByValue(tokenValue);

        if (optionalToken.isEmpty()) {
            return Optional.empty();
        }

        Token token = optionalToken.get();

        if (Instant.now().isAfter(token.getExpirationAt())) {
            return Optional.empty();
        }

        if (!token.getUser().getRealm().equals(realm)) {
            return Optional.empty();
        }

        return optionalToken;
    }
}
