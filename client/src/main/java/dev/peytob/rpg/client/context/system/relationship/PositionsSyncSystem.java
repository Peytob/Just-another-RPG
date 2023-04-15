package dev.peytob.rpg.client.context.system.relationship;

import dev.peytob.rpg.client.context.component.basic.PositionComponent;
import dev.peytob.rpg.client.context.component.relation.EntitiesPositionsSyncComponent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public final class PositionsSyncSystem implements System {

    @Override
    public void execute(EcsContext context) {
        Collection<EntitiesPositionsSyncComponent> syncComponents = context
            .getUnmodifiableComponentManager().getAllByType(EntitiesPositionsSyncComponent.class);

        syncComponents.forEach(component -> {
            Entity source = component.getSource();
            Entity target = component.getTarget();

            if (!source.isAlive() || !target.isAlive()) {
                // TODO Delete relationship on destroying one of entities
            }

            PositionComponent sourcePosition = source.getComponent(PositionComponent.class);
            PositionComponent targetPosition = target.getComponent(PositionComponent.class);

            if (sourcePosition != null && targetPosition != null) {
                targetPosition.setPosition(sourcePosition.getPosition());
            }
        });
    }
}
