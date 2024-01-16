package dev.peytob.rpg.client.network.service;

import org.springframework.web.client.RestTemplate;

public interface AuthService {

    String login(String username, String password, RestTemplate restTemplate);

    void logout(RestTemplate restTemplate);
}
