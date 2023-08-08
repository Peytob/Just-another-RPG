package dev.peytob.rpg.server.base.resource.world;

import dev.peytob.rpg.core.module.base.model.world.tilemap.Tilemap;
import dev.peytob.rpg.engine.resource.Resource;
import dev.peytob.rpg.server.base.resource.world.entity.Entity;

import java.util.ArrayList;
import java.util.Collection;

public class World implements Resource {

    private final Integer id;

    private final String textId;

    private final Tilemap tilemap;

    private final Collection<Entity> entities;

    public World(Integer id, String textId, Tilemap tilemap) {
        this.id = id;
        this.textId = textId;
        this.tilemap = tilemap;
        this.entities = new ArrayList<>();
    }

    public Integer id() {
        return id;
    }

    public String textId() {
        return textId;
    }

    public Tilemap getTilemap() {
        return tilemap;
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
