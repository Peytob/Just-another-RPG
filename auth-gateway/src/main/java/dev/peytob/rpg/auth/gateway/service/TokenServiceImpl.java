package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.entity.Token;
import dev.peytob.rpg.auth.gateway.entity.TokenType;
import dev.peytob.rpg.auth.gateway.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService {

    private final TokenCrudService tokenCrudService;

    private final HashService hashService;

    @Override
    @Transactional
    public String createUserToken(User user, TokenType tokenType) {
        String realmName = user.getRealm().getName();
        log.info("Creating new {} token for user with username {} in realm {}", tokenType, user.getUsername(), realmName);

        String tokenValue = generateTokenValue();
        String tokenHash = hashService.hashTokenString(tokenValue);

        Token token = Token.builder()
            .hash(tokenHash)
            .user(user)
            .type(tokenType)
            .expirationAt(generateExpirationTime(tokenType))
            .build();

        tokenCrudService.saveToken(token);

        log.info("Saved new {} token for user with username {} in realm {} with id {}", tokenType, user.getUsername(), realmName, token.getId());

        return tokenValue;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Token> getTokenByValue(String tokenValue) {
        String tokenHash = hashService.hashTokenString(tokenValue);
        return tokenCrudService.findTokenByHash(tokenHash);
    }

    @Override
    public void removeToken(Token token) {
        tokenCrudService.deleteToken(token);
    }

    private Instant generateExpirationTime(TokenType tokenType) {
        Instant now = Instant.now();
        return switch (tokenType) {
            // TODO Create properties
            case SESSION -> now.plus(4, ChronoUnit.HOURS);
            case LONGTIME -> Instant.MAX;
        };
    }

    private String generateTokenValue() {
        return RandomStringUtils.randomAlphanumeric(32);
    }
}
