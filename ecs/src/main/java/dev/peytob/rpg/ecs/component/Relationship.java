package dev.peytob.rpg.ecs.component;

import dev.peytob.rpg.ecs.entity.Entity;

import java.util.Objects;

public abstract class Relationship implements Component {

    private Entity source;

    private Entity target;

    public Relationship(Entity source, Entity target) {
        setSource(source);
        setTarget(target);
    }

    public final Entity getSource() {
        return source;
    }

    public final Entity getTarget() {
        return target;
    }

    public void setSource(Entity source) {
        this.source = Objects.requireNonNull(source);
    }

    public void setTarget(Entity target) {
        this.target = Objects.requireNonNull(target);
    }
}
