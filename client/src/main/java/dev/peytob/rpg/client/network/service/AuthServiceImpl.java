package dev.peytob.rpg.client.network.service;

import dev.peytob.rpg.client.network.exception.LoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static dev.peytob.rpg.client.network.constant.DefaultHeaders.AUTHORIZATION_HEADER;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Override
    public String login(String username, String password, RestTemplate restTemplate) {
        LoginDto loginDto = new LoginDto(username, password);
        ResponseEntity<Void> loginResult = restTemplate.postForEntity("/auth/login", loginDto, Void.class);

        if (loginResult.getStatusCode().is5xxServerError()) {
            log.error("Bad backend response during login: {}", loginResult.getStatusCode());
            throw new LoginException("Bad gateway while authorization");
        }

        List<String> authorizationHeaders = loginResult.getHeaders().getOrEmpty(AUTHORIZATION_HEADER);

        if (authorizationHeaders.isEmpty()) {
            log.error("Authorization header not found");
            throw new LoginException("Authorization failed");
        }

        return authorizationHeaders.get(0);
    }

    @Override
    public void logout(RestTemplate restTemplate) {
        ResponseEntity<Void> logoutResult = restTemplate.postForEntity("/auth/logout", "", Void.class);

        if (logoutResult.getStatusCode().is2xxSuccessful()) {
            return;
        }

        log.error("Bad response during logout from server: {}", logoutResult.getStatusCode());
        // TODO Exception
    }

    private record LoginDto(
        String username,
        String password
    ) {}
}
