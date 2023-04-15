package dev.peytob.rpg.client.context.component.relation;

import dev.peytob.rpg.ecs.component.Component;
import dev.peytob.rpg.ecs.entity.Entity;

public final class EntitiesPositionsSyncComponent implements Component {

    private Entity source;

    private Entity target;

    public EntitiesPositionsSyncComponent(Entity source, Entity target) {
        this.source = source;
        this.target = target;
    }

    public Entity getSource() {
        return source;
    }

    public void setSource(Entity source) {
        this.source = source;
    }

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }
}
