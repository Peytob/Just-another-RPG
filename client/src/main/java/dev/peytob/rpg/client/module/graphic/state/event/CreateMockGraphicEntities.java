package dev.peytob.rpg.client.module.graphic.state.event;

import dev.peytob.rpg.client.module.graphic.context.component.CameraComponent;
import dev.peytob.rpg.client.module.graphic.model.Camera;
import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.client.fsm.event.instance.StateSetUpEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.math.vector.Vectors.immutableVec2;
import static dev.peytob.rpg.math.vector.Vectors.immutableVec2i;

@Component
public class CreateMockGraphicEntities {

    @EventListener
    public void createMockGraphicEntities(StateSetUpEvent stateSetUpEvent) {
        EcsContextBuilder contextBuilder = stateSetUpEvent.contextBuilder();

        contextBuilder
            .initializeEntity(((entity, ecsContext) ->
                entity.bindComponent(new CameraComponent(new Camera(immutableVec2(0.0f, 0.0f), immutableVec2i(800, 600))))));
    }
}
