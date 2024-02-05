package dev.peytob.rpg.client.gameplay.event.handler;

import dev.peytob.rpg.client.fsm.event.instance.AfterEngineStatePushEvent;
import dev.peytob.rpg.client.fsm.state.InGameState;
import dev.peytob.rpg.client.gameplay.constant.DefaultClientEntityId;
import dev.peytob.rpg.core.gameplay.ecs.component.PositionComponent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.entity.Entity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.math.vector.Vectors.immutableVec2;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateInGameCharacterEntityEventHandler {

    private final InGameState inGameState;

    @EventListener
    void createInGameCharacterEntity(AfterEngineStatePushEvent afterEngineStatePushEvent) {
        if (!afterEngineStatePushEvent.executingEngineState().engineState().equals(inGameState)) {
            return;
        }

        log.info("Creating main character entity");

        EcsContext ecsContext = afterEngineStatePushEvent.executingEngineState().ecsContext();

        Entity entity = ecsContext.createEntity(DefaultClientEntityId.MAIN_CHARACTER_ENTITY_ID);
        entity.bindComponent(new PositionComponent(immutableVec2()));

        log.info("Main character entity created with id {}", entity.getId());
    }
}
