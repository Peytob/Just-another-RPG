package dev.peytob.rpg.server.server.service;

import dev.peytob.rpg.server.base.resource.User;
import dev.peytob.rpg.server.base.service.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void login(String token) {
        // TODO Temporary just creates new player

        // Getting user id by token...
        // Getting user data by id...
        // Making token -> user cache
        userService.createUser(new User(token.hashCode(), token, token));
        // Sending player login events...
    }

    @Override
    public void logout(String token) {
        // Getting player by token...
        // Invalidating token -> user cache...
        userService.removeUser(new User(token.hashCode(), token, token));
        // Sending player logout events...
    }
}
