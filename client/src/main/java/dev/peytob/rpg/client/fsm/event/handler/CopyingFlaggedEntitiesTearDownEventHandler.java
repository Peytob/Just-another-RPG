package dev.peytob.rpg.client.fsm.event.handler;

import dev.peytob.rpg.client.context.component.CopyEntityOnChangeStateFlag;
import dev.peytob.rpg.ecs.component.Relationship;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.client.fsm.event.instance.StateTearDownEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class CopyingFlaggedEntitiesTearDownEventHandler {

    @EventListener
    public void moveLoadedDataToNextState(StateTearDownEvent stateTearDownEvent) {
        EcsContextBuilder contextBuilder = stateTearDownEvent.ecsContextBuilder();
        EcsContext context = stateTearDownEvent.context();

        context
            .getComponentsByType(CopyEntityOnChangeStateFlag.class)
            .stream()
            .map(context::getComponentEntity)
            .forEach(entity -> copyEntityToNextContext(entity, contextBuilder));

        // TODO Create "CopyRelationship" component and event handler!
    }

    private void copyEntityToNextContext(Entity entity, EcsContextBuilder contextBuilder) {
        Collection<dev.peytob.rpg.ecs.component.Component> components = List.copyOf(entity.getComponents());

        contextBuilder.initializeEntity((newEntity, context) ->
            components.stream()
                .filter(component -> !(component instanceof Relationship))
                .filter(component -> !(component instanceof CopyEntityOnChangeStateFlag))
                .forEach(newEntity::bindComponent),
            entity.getId());
    }
}
