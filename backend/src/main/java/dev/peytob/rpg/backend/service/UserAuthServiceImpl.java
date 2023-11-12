package dev.peytob.rpg.backend.service;

import dev.peytob.rpg.backend.dto.auth.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final AuthProvider authProvider;

    @Override
    public Optional<String> loginUser(String username, String password) {
        // TODO Custom exception
        return authProvider.login(username, password);
    }

    @Override
    public void logoutUser(String token) {
        authProvider.logout(token);
    }

    @Override
    public Optional<TokenDto> validateToken(String token) {
        return authProvider.validate(token);
    }

    @Override
    public String registerUser(String username, String password, String email) {
        return authProvider.register(username, password, email);
    }
}
