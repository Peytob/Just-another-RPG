package dev.peytob.rpg.server.base.resource.world;

import dev.peytob.rpg.core.gameplay.model.world.tilemap.layer.TilemapLayer;
import dev.peytob.rpg.engine.resource.Resource;
import dev.peytob.rpg.server.base.resource.world.entity.Entity;

import java.util.ArrayList;
import java.util.Collection;

public class World implements Resource {

    private final String id;

    private final TilemapLayer tilemapLayer;

    private final Collection<Entity> entities;

    public World(String id, TilemapLayer tilemapLayer) {
        this.id = id;
        this.tilemapLayer = tilemapLayer;
        this.entities = new ArrayList<>();
    }

    @Override
    public String id() {
        return id;
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
