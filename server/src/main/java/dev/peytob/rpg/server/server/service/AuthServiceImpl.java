package dev.peytob.rpg.server.server.service;

import dev.peytob.rpg.server.base.resource.entity.Player;
import dev.peytob.rpg.server.base.service.player.PlayerService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final PlayerService playerService;

    public AuthServiceImpl(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public void login(String token) {
        // TODO Temporary just creates new player

        // Getting user id by token...
        // Getting user data by id...
        // Making token -> user cache
        playerService.createPlayer(new Player(token.hashCode(), token));
        // Sending player login events...
    }

    @Override
    public void logout(String token) {
        // Getting player by token...
        // Invalidating token -> user cache...
        playerService.removePlayer(new Player(token.hashCode(), token));
        // Sending player logout events...
    }
}
