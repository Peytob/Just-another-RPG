package dev.peytob.rpg.backend.service;

import dev.peytob.rpg.backend.dto.auth.TokenDto;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Deprecated
public class AuthGatewayAuthProvider implements AuthProvider {

    public static String AUTH_GATEWAY_AUTHORIZATION_HEADER = "Authorization";

    private final RestTemplate restTemplate;

    public AuthGatewayAuthProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<String> login(String username, String password) {
        AuthGatewayLoginDto request = new AuthGatewayLoginDto(username, password);
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("/auth/login", request, Void.class);
        return responseEntity.getHeaders().getOrEmpty(AUTH_GATEWAY_AUTHORIZATION_HEADER).stream().findFirst();
    }

    @Override
    public void logout(String token) {
        restTemplate.postForEntity("/auth/logout", null, Void.class);
    }

    @Override
    public Optional<TokenDto> validate(String token) {
        RequestEntity<Void> request = RequestEntity.post("/auth/validate")
            .header(AUTH_GATEWAY_AUTHORIZATION_HEADER, token)
            .build();

        ResponseEntity<TokenDto> response = restTemplate.exchange(request, TokenDto.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            return Optional.empty();
        }

        return Optional.ofNullable(response.getBody());
    }

    @Override
    public String register(String username, String password, String email) {
        AuthGatewayRegistrationDto request = new AuthGatewayRegistrationDto(password, username, email);
        ResponseEntity<Void> response = restTemplate.postForEntity("/auth/register", request, Void.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            // TODO Make custom exception
            throw new IllegalStateException();
        }

        return response.getHeaders().getOrEmpty(AUTH_GATEWAY_AUTHORIZATION_HEADER).stream().findFirst().orElseThrow();
    }

    private record AuthGatewayLoginDto(
        String username,
        String password
    ) { }

    public record AuthGatewayRegistrationDto(
        String password,

        String username,

        String email
    ) { }
}
