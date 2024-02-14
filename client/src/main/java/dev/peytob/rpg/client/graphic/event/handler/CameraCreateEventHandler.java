package dev.peytob.rpg.client.graphic.event.handler;

import dev.peytob.rpg.client.fsm.event.instance.BeforeEngineStatePushEvent;
import dev.peytob.rpg.client.graphic.ecs.component.CameraComponent;
import dev.peytob.rpg.client.graphic.model.Camera;
import dev.peytob.rpg.client.graphic.model.glfw.Window;
import dev.peytob.rpg.math.vector.Vec2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.client.graphic.model.RenderingConstants.RENDERING_COEFFICIENT;
import static dev.peytob.rpg.math.vector.Vectors.immutableVec2;

@Component
@RequiredArgsConstructor
@Slf4j
public class CameraCreateEventHandler {

    private final Window window;

    @EventListener
    void renderingContextCreate(BeforeEngineStatePushEvent beforeEngineStatePushEvent) {
        // TODO Temporary solution
        beforeEngineStatePushEvent.contextBuilder().initializeEntity((entity, ecsContext) -> {
            log.info("Creating camera ECS entity");
            Vec2 windowResolution = immutableVec2(window.getWidth(), window.getHeight()).division(RENDERING_COEFFICIENT);
            Camera camera = new Camera(windowResolution);
            CameraComponent cameraComponent = new CameraComponent(camera);
            entity.bindComponent(cameraComponent);
        }, "Camera");
    }
}
