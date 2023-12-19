package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.entity.Token;
import dev.peytob.rpg.auth.gateway.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenCrudServiceImpl implements TokenCrudService {

    private final TokenRepository tokenRepository;

    @Override
    @Transactional
    public Token saveToken(Token token) {
        return tokenRepository.save(token);
    }

    @Override
    public Optional<Token> findTokenByHash(String tokenHash) {
        return tokenRepository.findByHash(tokenHash);
    }

    @Override
    public void deleteToken(Token token) {
        tokenRepository.delete(token);
    }

    @Override
    public Collection<Token> findExpiredTokens() {
        return tokenRepository.findByExpirationAtAfter(Instant.now());
    }

    @Override
    public void deleteTokens(Collection<Token> expiredTokens) {
        tokenRepository.deleteAll(expiredTokens);
    }
}
