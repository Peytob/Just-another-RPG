package dev.peytob.rpg.server.server.service;

import dev.peytob.rpg.server.base.resource.User;
import dev.peytob.rpg.server.base.service.user.UserService;
import dev.peytob.rpg.server.server.event.auth.instance.UserLoginEvent;
import dev.peytob.rpg.server.server.event.auth.instance.UserLogoutEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserService userService;

    private final ServerEventBus serverEventBus;

    public AuthServiceImpl(UserService userService, ServerEventBus serverEventBus) {
        this.userService = userService;
        this.serverEventBus = serverEventBus;
    }

    @Override
    public void login(String token) {
        log.info("User is trying to login in server");

        // Getting user id by token...
        // Getting user data by id...
        // Making token -> user cache
        User user = new User(token, token);
        userService.createUser(user);
        serverEventBus.publishServerEvent(new UserLoginEvent(user.id()));
    }

    @Override
    public void logout(String token) {
        log.info("User is logout from server");

        // Getting player by token...
        // Invalidating token -> user cache...
        User user = new User(token, token);
        userService.removeUser(user);
        serverEventBus.publishServerEvent(new UserLogoutEvent(user.id()));
    }
}
