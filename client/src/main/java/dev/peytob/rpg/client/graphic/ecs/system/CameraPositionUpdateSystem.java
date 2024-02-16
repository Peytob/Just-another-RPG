package dev.peytob.rpg.client.graphic.ecs.system;

import dev.peytob.rpg.client.fsm.annotation.IncludeInAllStates;
import dev.peytob.rpg.client.graphic.ecs.component.CameraComponent;
import dev.peytob.rpg.client.graphic.service.DefaultUniformBlocksService;
import dev.peytob.rpg.core.gameplay.ecs.component.PositionComponent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.client.gameplay.constant.DefaultClientEntityId.MAIN_CHARACTER_ENTITY_ID;
import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.RENDERING;

@RequiredArgsConstructor
@IncludeInAllStates(order = RENDERING)
@Component
public class CameraPositionUpdateSystem implements System {

    private final DefaultUniformBlocksService defaultUniformBlocksService;

    @Override
    public void execute(EcsContext context) {
        context.getSingletonComponentByType(CameraComponent.class)
            .map(CameraComponent::getCamera)
            .ifPresent(camera -> {
                context.getEntityById(MAIN_CHARACTER_ENTITY_ID)
                    .map(entity -> entity.getComponent(PositionComponent.class))
                    .ifPresent(playerPosition -> {
                        camera.setPosition(playerPosition.getPosition());
                    });
            });
    }
}
