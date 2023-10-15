package dev.peytob.rpg.server.base.resource;

import dev.peytob.rpg.engine.resource.Resource;
import dev.peytob.rpg.server.base.resource.world.entity.Player;

public final class User implements Resource {
    private final String id;
    private final String token;
    private Player worldPlayer;

    public User(String id, String token) {
        this.id = id;
        this.token = token;
    }

    @Override
    public String id() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Player getWorldPlayer() {
        return worldPlayer;
    }

    public void setWorldPlayer(Player worldPlayer) {
        this.worldPlayer = worldPlayer;
    }
}
