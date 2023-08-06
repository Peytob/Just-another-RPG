package dev.peytob.rpg.server.base.resource.world.entity;

import dev.peytob.rpg.server.base.resource.User;
import dev.peytob.rpg.server.base.resource.world.World;

public class Player extends Entity {

    private final User connectedUser;

    public Player(World world, User connectedUser) {
        super(world, connectedUser.textId());
        this.connectedUser = connectedUser;
    }

    public User getConnectedUser() {
        return connectedUser;
    }
}
