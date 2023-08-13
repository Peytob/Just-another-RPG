package dev.peytob.rpg.server.base.event.player;

import dev.peytob.rpg.server.base.resource.User;
import dev.peytob.rpg.server.base.resource.world.entity.Player;
import dev.peytob.rpg.server.base.service.user.UserService;
import dev.peytob.rpg.server.server.event.auth.instance.UserLoginEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class WorldPlayerOnLogoutDestroyer {

    private final UserService userService;

    public WorldPlayerOnLogoutDestroyer(UserService userService) {
        this.userService = userService;
    }

    @EventListener(UserLoginEvent.class)
    public void removeUserPlayerFromWorld(UserLoginEvent userLoginEvent) {
        User user = userService.getUserById(userLoginEvent.userId());

        Player worldPlayer = user.getWorldPlayer();

        if (worldPlayer == null) {
            return;
        }

        worldPlayer.getWorld().removeEntity(worldPlayer);
    }
}
