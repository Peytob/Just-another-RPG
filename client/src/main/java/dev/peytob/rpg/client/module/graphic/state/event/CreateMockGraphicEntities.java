package dev.peytob.rpg.client.module.graphic.state.event;

import dev.peytob.rpg.client.module.graphic.context.component.CameraComponent;
import dev.peytob.rpg.client.module.graphic.model.Camera;
import dev.peytob.rpg.client.state.InGameEngineState;
import dev.peytob.rpg.core.module.location.context.component.TilemapComponent;
import dev.peytob.rpg.core.module.location.model.tilemap.Tilemaps;
import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.engine.state.event.StateSetUpEventHandler;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.math.vector.Vectors.immutableVec2;
import static dev.peytob.rpg.math.vector.Vectors.immutableVec2i;

@Component
public class CreateMockGraphicEntities extends StateSetUpEventHandler<InGameEngineState> {

    @Override
    public void onStateSetUp(InGameEngineState engineState, EcsContextBuilder contextBuilder) {
        contextBuilder
            .initializeEntity((entity, ecsContext) ->
                entity.bindComponent(new TilemapComponent(Tilemaps.mutable(immutableVec2i(16, 16)))))
            .initializeEntity(((entity, ecsContext) ->
                entity.bindComponent(new CameraComponent(new Camera(immutableVec2(0.0f, 0.0f), immutableVec2i(800, 600))))));
    }
}
