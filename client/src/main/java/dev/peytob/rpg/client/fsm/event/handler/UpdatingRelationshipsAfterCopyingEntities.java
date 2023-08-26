package dev.peytob.rpg.client.fsm.event.handler;

import dev.peytob.rpg.client.fsm.event.instance.StateContextRefreshed;
import dev.peytob.rpg.ecs.component.Relationship;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.entity.Entity;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UpdatingRelationshipsAfterCopyingEntities {

    @EventListener
    public void moveLoadedDataToNextState(StateContextRefreshed stateContextRefreshed) {
        EcsContext context = stateContextRefreshed.refreshedEcsContext();

        context
            .getAllEntities()
            .forEach(entity -> updateEntityRelationships(context, entity));
    }

    private void updateEntityRelationships(EcsContext ecsContext, Entity entity) {
        entity.getComponents().stream()
            .filter(component -> component instanceof Relationship)
            .map(component -> (Relationship) component)
            .forEach(relationship -> {
                Entity source = ecsContext.getEntityById(relationship.getSource().getId())
                        .orElseThrow(() -> new IllegalStateException("Source of relationship " + relationship + " not found in new context"));

                Entity target = ecsContext.getEntityById(relationship.getTarget().getId())
                        .orElseThrow(() -> new IllegalStateException("Target of relationship " + relationship + " not found in new context"));

                relationship.setSource(source);
                relationship.setTarget(target);
            });
    }
}
