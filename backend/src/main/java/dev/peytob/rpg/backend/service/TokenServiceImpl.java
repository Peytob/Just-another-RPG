package dev.peytob.rpg.backend.service;

import dev.peytob.rpg.backend.entity.TokenEntity;
import dev.peytob.rpg.backend.entity.TokenType;
import dev.peytob.rpg.backend.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService {

    public static final Duration SESSION_TOKEN_LIFETIME = Duration.ofHours(3);

    private final TokenCrudService tokenCrudService;

    private final TokenEncoder tokenEncoder;

    @Override
    @Transactional
    public String createUserSessionToken(UserEntity user) {
        Optional<TokenEntity> currentSessionToken = tokenCrudService.findUserSessionToken(user);
        currentSessionToken.ifPresent(this::removeToken);

        String rawToken = generateRawToken();
        String encodedToken = tokenEncoder.encode(rawToken);

        TokenEntity token = TokenEntity.builder()
            .user(user)
            .encodedToken(encodedToken)
            .expirationAt(Instant.now().plus(SESSION_TOKEN_LIFETIME))
            .type(TokenType.SESSION)
            .build();

        tokenCrudService.saveToken(token);

        return rawToken;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TokenEntity> getTokenByRawValue(String rawToken) {
        String encodedToken = tokenEncoder.encode(rawToken);
        return tokenCrudService.findByEncodedToken(encodedToken);
    }

    @Override
    @Transactional
    public void removeToken(TokenEntity token) {
        tokenCrudService.removeToken(token);
    }

    private String generateRawToken() {
        return RandomStringUtils.randomAlphanumeric(24);
    }
}
