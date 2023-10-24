package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.entity.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenCrudServiceImpl implements TokenCrudService {

    private final TokenRepository tokenRepository;

    @Override
    @Transactional
    public Token saveToken(Token token) {
        return tokenRepository.save(token);
    }
}
