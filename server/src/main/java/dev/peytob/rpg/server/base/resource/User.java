package dev.peytob.rpg.server.base.resource;

import dev.peytob.rpg.engine.resource.Resource;
import dev.peytob.rpg.server.base.resource.world.entity.Player;

public final class User implements Resource {
    private final Integer id;
    private final String textId;
    private final String token;
    private Player worldPlayer;

    public User(Integer id, String textId, String token) {
        this.id = id;
        this.textId = textId;
        this.token = token;
    }

    @Override
    public Integer id() {
        return id;
    }

    @Override
    public String textId() {
        return textId;
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
