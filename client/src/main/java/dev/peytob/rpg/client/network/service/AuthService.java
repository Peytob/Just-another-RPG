package dev.peytob.rpg.client.network.service;

import org.springframework.web.client.RestTemplate;

public interface AuthService {

    String login(String login, String password, RestTemplate restTemplate);

    void logout();
}
