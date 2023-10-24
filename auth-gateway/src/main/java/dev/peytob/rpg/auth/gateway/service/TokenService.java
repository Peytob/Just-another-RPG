package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.entity.TokenType;
import dev.peytob.rpg.auth.gateway.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {

    String createUserToken(User user, TokenType tokenType);
}
