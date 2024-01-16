package dev.peytob.rpg.client.network.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public String login(String login, String password, RestTemplate restTemplate) {
        return null;
    }

    @Override
    public void logout() {

    }
}
