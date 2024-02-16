package dev.peytob.rpg.client.gameplay.ecs.system;

import dev.peytob.rpg.client.fsm.annotation.IncludeInState;
import dev.peytob.rpg.client.fsm.state.InGameState;
import dev.peytob.rpg.client.gameplay.ecs.component.WorldComponent;
import dev.peytob.rpg.client.gameplay.ecs.event.WorldUpdatedEvent;
import dev.peytob.rpg.core.gameplay.ecs.component.PositionComponent;
import dev.peytob.rpg.core.gameplay.repository.WorldRepository;
import dev.peytob.rpg.core.gameplay.resource.World;
import dev.peytob.rpg.core.network.model.server.WorldState;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.system.System;
import dev.peytob.rpg.math.vector.Vec2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static dev.peytob.rpg.client.gameplay.constant.DefaultClientEntityId.MAIN_CHARACTER_ENTITY_ID;
import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.WORLD_STATE_UPDATING;

@Component
@IncludeInState(value = InGameState.class, order = WORLD_STATE_UPDATING)
@RequiredArgsConstructor
@Slf4j
public class WorldStateUpdateSystem implements System {
    
    private final WorldRepository worldRepository;

    @Override
    public void execute(EcsContext context) {
        Collection<WorldUpdatedEvent> worldUpdatedEvents = List.copyOf(context.getEventsByType(WorldUpdatedEvent.class));
        worldUpdatedEvents.forEach(event -> handleEvent(event.getWorldState(), context));
        context.removeAllEventsByType(WorldUpdatedEvent.class);
    }

    private void handleEvent(WorldState worldState, EcsContext context) {
        String worldId = worldState.loadedWorld().id();

        World world = worldRepository.getById(worldId).orElseThrow();

        context.getSingletonComponentByType(WorldComponent.class).ifPresentOrElse(worldComponent ->
            worldComponent.setWorld(world),

        () -> {
            log.info("World not found, creating new world entity");
            Entity entity = context.createEntity();
            entity.bindComponent(new WorldComponent(world));
        });

        context.getEntityById(MAIN_CHARACTER_ENTITY_ID).ifPresent(mainCharacterEntity -> {
            Vec2 position = worldState.characterInfo().position();
            PositionComponent positionComponent = mainCharacterEntity.getComponent(PositionComponent.class);

            if (positionComponent != null) {
//                positionComponent.setPosition(position); TODO now is always 0
            } else {
                positionComponent = new PositionComponent(position);
                mainCharacterEntity.bindComponent(positionComponent);
            }
        });
    }
}
