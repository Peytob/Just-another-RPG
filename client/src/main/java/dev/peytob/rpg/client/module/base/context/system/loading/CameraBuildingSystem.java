package dev.peytob.rpg.client.module.base.context.system.loading;

import dev.peytob.rpg.client.context.component.CopyEntityOnChangeStateFlag;
import dev.peytob.rpg.client.fsm.annotation.IncludeInState;
import dev.peytob.rpg.client.fsm.state.instance.InGameLoadingState;
import dev.peytob.rpg.client.module.graphic.context.component.CameraComponent;
import dev.peytob.rpg.client.module.graphic.model.Camera;
import dev.peytob.rpg.client.module.graphic.model.Window;
import dev.peytob.rpg.core.module.base.context.component.position.EntitiesPositionsSyncRelationship;
import dev.peytob.rpg.core.module.base.context.component.position.PositionComponent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.client.module.base.context.constant.DefaultContextEntitiesIds.CAMERA_ENTITY_ID;
import static dev.peytob.rpg.client.module.base.context.constant.DefaultContextEntitiesIds.PLAYER_ENTITY_ID;
import static dev.peytob.rpg.math.vector.Vectors.immutableVec2;
import static dev.peytob.rpg.math.vector.Vectors.immutableVec2i;

@Component
@IncludeInState(state = InGameLoadingState.class)
public class CameraBuildingSystem implements System {

    private final Window window;

    public CameraBuildingSystem(Window window) {
        this.window = window;
    }

    @Override
    public void execute(EcsContext context) {
        // TODO Get data from server...

        if (context.getSingletonComponentByType(CameraComponent.class).isEmpty()) {
            Entity playerEntity = context.getEntityById(PLAYER_ENTITY_ID).orElseThrow();
            Camera camera = new Camera(immutableVec2(0.0f, 0.0f), immutableVec2i(window.getWidth(), window.getHeight()));

            Entity cameraEntity = context.createEntity(CAMERA_ENTITY_ID);
            cameraEntity.bindComponent(new CameraComponent(camera));
            cameraEntity.bindComponent(new PositionComponent(immutableVec2()));
            cameraEntity.bindComponent(new EntitiesPositionsSyncRelationship(playerEntity, cameraEntity));
            cameraEntity.bindComponent(new CopyEntityOnChangeStateFlag());
        }
    }
}