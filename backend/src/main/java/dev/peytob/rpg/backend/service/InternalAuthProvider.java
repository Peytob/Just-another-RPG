package dev.peytob.rpg.backend.service;

import dev.peytob.rpg.backend.dto.auth.TokenDto;
import dev.peytob.rpg.backend.entity.TokenEntity;
import dev.peytob.rpg.backend.entity.UserEntity;
import dev.peytob.rpg.backend.exception.LoginFailed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
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
        UserEntity user = userService.findUserByCredentials(username, password).orElseThrow(() -> {
            log.debug("User with username '{}' and password **** not found", username);
            return new LoginFailed();
        });

        return performLogin(user).describeConstable();
    }

    @Override
    public void logoutUser(String tokenValue) {
        TokenEntity token = tokenService.getTokenByRawValue(tokenValue).orElseThrow(LoginFailed::new);
        tokenService.removeToken(token);
    }

    @Override
    public Optional<TokenDto> validateRawToken(String rawToken) {
        return tokenService.getTokenByRawValue(rawToken)
            .filter(token -> Instant.now().isAfter(token.getExpirationAt()))
            .map(this::toTokenDto);
    }

    @Override
    public String registerPlayerUser(String username, String password, String email) {
        UserEntity user = userService.createPlayerUser(username, password, email);
        return performLogin(user);
    }

    private TokenDto toTokenDto(TokenEntity tokenEntity) {
        return new TokenDto(
            tokenEntity.getUser().getUsername(),
            tokenEntity.getUser().getRoles(),
            tokenEntity.getType(),
            tokenEntity.getExpirationAt());
    }

    private String performLogin(UserEntity user) {
        user.setLastLoginAt(Instant.now());
        return tokenService.createUserSessionToken(user);
    }
}
