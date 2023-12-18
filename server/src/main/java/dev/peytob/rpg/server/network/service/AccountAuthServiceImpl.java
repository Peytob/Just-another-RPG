package dev.peytob.rpg.server.network.service;

import dev.peytob.rpg.server.network.dto.TokenDto;
import dev.peytob.rpg.server.network.exception.LoginFailed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountAuthServiceImpl implements AccountAuthService {

    private static final String BACKEND_AUTHORIZATION_HEADER = "Authorization";

    @Qualifier("backendRestTemplate")
    private final RestTemplate restTemplate;

    @Override
    public String login(String username, String password) {
        LoginDto loginDto = new LoginDto(username, password);

        try {
            ResponseEntity<Void> response = restTemplate.postForEntity("/auth/login", loginDto, Void.class);
            List<String> authenticationHeaders = response.getHeaders().getOrEmpty("Authorization");
            return authenticationHeaders.get(0);
        } catch (HttpClientErrorException.Unauthorized unauthorized) {
            throw new LoginFailed("Bad credentials", unauthorized);
        } catch (HttpClientErrorException httpClientErrorException) {
            throw new LoginFailed("Backend error, try again later", httpClientErrorException);
        }}

    @Override
    public void logout(String token) {
        RequestEntity<Void> request = RequestEntity.post("/auth/logout")
            .header(BACKEND_AUTHORIZATION_HEADER, token)
            .build();

        restTemplate.exchange(request, TokenDto.class);
    }

    @Override
    public Optional<TokenDto> validate(String token) {
        RequestEntity<Void> request = RequestEntity.post("/auth/validate")
            .header(BACKEND_AUTHORIZATION_HEADER, token)
            .build();

        ResponseEntity<TokenDto> response = restTemplate.exchange(request, TokenDto.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            return Optional.empty();
        }

        return Optional.ofNullable(response.getBody());
    }

    private record LoginDto(
        String username,
        String password
    ) {
    }
}