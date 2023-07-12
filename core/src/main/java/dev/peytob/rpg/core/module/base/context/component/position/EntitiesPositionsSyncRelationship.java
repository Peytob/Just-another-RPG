package dev.peytob.rpg.core.module.base.context.component.position;

import dev.peytob.rpg.ecs.component.Relationship;
import dev.peytob.rpg.ecs.entity.Entity;

public final class EntitiesPositionsSyncRelationship extends Relationship {

    public EntitiesPositionsSyncRelationship(Entity source, Entity target) {
        super(source, target);
    }
}
