package dev.peytob.rpg.server.base.resource.world;

import dev.peytob.rpg.core.gameplay.model.world.tilemap.layer.TilemapLayer;
import dev.peytob.rpg.engine.resource.Resource;
import dev.peytob.rpg.server.base.resource.world.entity.Entity;

import java.util.ArrayList;
import java.util.Collection;

public class World implements Resource {

    private final Integer id;

    private final String textId;

    private final TilemapLayer tilemapLayer;

    private final Collection<Entity> entities;

    public World(Integer id, String textId, TilemapLayer tilemapLayer) {
        this.id = id;
        this.textId = textId;
        this.tilemapLayer = tilemapLayer;
        this.entities = new ArrayList<>();
    }

    public Integer id() {
        return id;
    }

    public String textId() {
        return textId;
    }

    public TilemapLayer getTilemap() {
        return tilemapLayer;
    }

    public Collection<Entity> getEntities() {
        return entities;
    }

    public boolean appendEntity(Entity entity) {
        return entities.add(entity);
    }

    public boolean removeEntity(Entity entity) {
        return entities.remove(entity);
    }
}
