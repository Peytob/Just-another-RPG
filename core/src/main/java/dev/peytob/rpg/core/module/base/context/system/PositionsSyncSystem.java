package dev.peytob.rpg.core.module.base.context.system;

import dev.peytob.rpg.core.module.base.context.component.position.PositionComponent;
import dev.peytob.rpg.core.module.base.context.component.position.EntitiesPositionsSyncRelationship;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public final class PositionsSyncSystem implements System {

    @Override
    public void execute(EcsContext context) {
        Collection<EntitiesPositionsSyncRelationship> syncComponents = context
            .getComponentsByType(EntitiesPositionsSyncRelationship.class);

        syncComponents.forEach(component -> {
            Entity source = component.getSource();
            Entity target = component.getTarget();

            if (!source.isAlive() || !target.isAlive()) {
                Entity entity = context.getComponentEntity(component);
                entity.removeComponent(component.getClass());
                return;
            }

            PositionComponent sourcePosition = source.getComponent(PositionComponent.class);
            PositionComponent targetPosition = target.getComponent(PositionComponent.class);

            if (sourcePosition != null && targetPosition != null) {
                targetPosition.setPosition(sourcePosition.getPosition());
            }
        });
    }
}
