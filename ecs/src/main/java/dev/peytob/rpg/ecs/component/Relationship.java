package dev.peytob.rpg.ecs.component;

import dev.peytob.rpg.ecs.entity.Entity;

public abstract class Relationship implements Component {

    private final Entity source;

    private final Entity target;

    public Relationship(Entity source, Entity target) {
        this.source = source;
        this.target = target;
    }

    public final Entity getSource() {
        return source;
    }

    public final Entity getTarget() {
        return target;
    }
}
