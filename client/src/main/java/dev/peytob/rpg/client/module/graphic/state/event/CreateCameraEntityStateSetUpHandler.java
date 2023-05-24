package dev.peytob.rpg.client.module.graphic.state.event;

import dev.peytob.rpg.client.module.graphic.context.component.CameraComponent;
import dev.peytob.rpg.client.module.graphic.model.Camera;
import dev.peytob.rpg.client.state.InGameEngineState;
import dev.peytob.rpg.core.module.base.context.component.PositionComponent;
import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.engine.state.event.handler.StateSetUpEventHandler;
import dev.peytob.rpg.math.vector.Vec2;
import dev.peytob.rpg.math.vector.Vectors;
import org.springframework.stereotype.Component;

@Component
public class CreateCameraEntityStateSetUpHandler extends StateSetUpEventHandler<InGameEngineState> {

    @Override
    public void onStateSetUp(InGameEngineState engineState, EcsContextBuilder contextBuilder) {
        contextBuilder.initializeEntity((entity, ecsContext) -> {
            Vec2 defaultCameraPosition = Vectors.immutableVec2(0, 0);
            Camera camera = new Camera(defaultCameraPosition, Vectors.immutableVec2i(800, 600));
            entity.bindComponent(new CameraComponent(camera));
            entity.bindComponent(new PositionComponent(defaultCameraPosition));
        });
    }
}
