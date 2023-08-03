package dev.peytob.rpg.server.base.resource.world.entity;

import dev.peytob.rpg.server.base.resource.User;
import dev.peytob.rpg.server.base.resource.world.World;

public class Player extends Entity {

    private final User connectedPlayer;

    public Player(World world, User connectedPlayer) {
        super(world, connectedPlayer.textId());
        this.connectedPlayer = connectedPlayer;
    }

    public User getConnectedPlayer() {
        return connectedPlayer;
    }
}
