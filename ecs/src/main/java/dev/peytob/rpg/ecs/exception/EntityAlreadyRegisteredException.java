package dev.peytob.rpg.ecs.exception;

import dev.peytob.rpg.ecs.entity.Entity;

public final class EntityAlreadyRegisteredException extends RuntimeException {

    private final Entity entity;

    public EntityAlreadyRegisteredException(Entity entity) {
        super("Entity already registered or entity with this id already exists");
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
