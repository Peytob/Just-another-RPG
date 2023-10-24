package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.entity.TokenType;
import dev.peytob.rpg.auth.gateway.entity.User;
import dev.peytob.rpg.auth.gateway.exception.LoginFailed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Optional<User> optionalUser = userService.getUserByCredentials(username, password, realm);

        if (optionalUser.isEmpty()) {
            log.info("User with username {} and password **** not found in realm {}", username, realm.getName());
            throw new LoginFailed();
        }

        User user = optionalUser.get();

        if (!user.isActivated()) {
            log.info("Deactivated user with username {} is trying to login in realm {}", username, realm.getName());
            throw new LoginFailed();
        }

        return tokenService.createUserToken(user, tokenType);
    }
}
