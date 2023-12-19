package dev.peytob.rpg.backend.service;

import dev.peytob.rpg.backend.entity.TokenEntity;
import dev.peytob.rpg.backend.entity.TokenType;
import dev.peytob.rpg.backend.entity.UserEntity;
import dev.peytob.rpg.backend.exception.UnresolvedReferencesConflictException;
import dev.peytob.rpg.backend.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenCrudServiceImpl implements TokenCrudService {

    private final TokenRepository tokenRepository;

    @Override
    public Optional<TokenEntity> findUserSessionToken(UserEntity user) {
        return tokenRepository.findByUserAndType(user, TokenType.SESSION);
    }

    @Override
    public TokenEntity saveToken(TokenEntity token) {
        return tokenRepository.save(token);
    }

    @Override
    public Optional<TokenEntity> findByEncodedToken(String encodedToken) {
        return tokenRepository.findByEncodedToken(encodedToken);
    }

    @Override
    public void removeToken(TokenEntity token) {
        try {
            tokenRepository.delete(token);
        } catch (ConstraintViolationException exception) {
            throw new UnresolvedReferencesConflictException("Some references to token still exists", exception);
        }

    }
}
