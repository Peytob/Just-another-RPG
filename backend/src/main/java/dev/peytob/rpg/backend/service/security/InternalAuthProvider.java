package dev.peytob.rpg.backend.service.security;

import dev.peytob.rpg.backend.dto.auth.TokenDto;
import dev.peytob.rpg.backend.entity.TokenEntity;
import dev.peytob.rpg.backend.entity.TokenType;
import dev.peytob.rpg.backend.entity.UserEntity;
import dev.peytob.rpg.backend.exception.LoginFailed;
import dev.peytob.rpg.backend.service.TokenService;
import dev.peytob.rpg.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class InternalAuthProvider implements AuthProvider {

    private final UserService userService;

    private final TokenService tokenService;

    @Override
    @Transactional
    public Optional<String> loginUser(String username, String password) {
        log.info("Login user with username '{}' by credentials", username);

        UserEntity user = userService.findUserByCredentials(username, password).orElseThrow(() -> {
            log.debug("User with username '{}' and password **** not found", username);
            return new LoginFailed();
        });

        return performLogin(user).describeConstable();
    }

    @Override
    @Transactional
    public void logoutUser(String tokenValue) {
        log.info("Logout user by token");

        TokenEntity token = tokenService
            .getTokenByRawValue(tokenValue)
            .filter(tokenEntity -> tokenEntity.getType().equals(TokenType.SESSION))
            .orElseThrow(() -> {
                log.debug("Session token with given raw value '{}' not found", tokenValue);
                return new LoginFailed();
            });

        tokenService.removeToken(token);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TokenDto> validateRawToken(String rawToken) {
        return tokenService.getTokenByRawValue(rawToken)
            .filter(token -> Instant.now().isBefore(token.getExpirationAt()))
            .map(this::toTokenDto);
    }

    @Override
    @Transactional
    public String registerPlayerUser(String username, String password, String email) {
        log.info("Registering new user with username '{}' and email '{}'", username, email);

        UserEntity user = userService.createPlayerUser(username, password, email);
        return performLogin(user);
    }

    private TokenDto toTokenDto(TokenEntity tokenEntity) {
        return new TokenDto(
            tokenEntity.getUser().getId(),
            tokenEntity.getUser().getUsername(),
            List.copyOf(tokenEntity.getUser().getRoles()),
            tokenEntity.getType(),
            tokenEntity.getExpirationAt());
    }

    private String performLogin(UserEntity user) {
        user.setLastLoginAt(Instant.now());
        return tokenService.createUserSessionToken(user);
    }
}
