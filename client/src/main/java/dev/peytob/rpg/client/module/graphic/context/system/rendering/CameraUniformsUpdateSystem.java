package dev.peytob.rpg.client.module.graphic.context.system.rendering;

import dev.peytob.rpg.client.module.graphic.context.component.CameraComponent;
import dev.peytob.rpg.client.module.graphic.service.facade.uniformblock.CameraUniformBlockFacade;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

@Component
public class CameraUniformsUpdateSystem implements System {

    private final CameraUniformBlockFacade cameraUniformBlockFacade;

    public CameraUniformsUpdateSystem(CameraUniformBlockFacade cameraUniformBlockFacade) {
        this.cameraUniformBlockFacade = cameraUniformBlockFacade;
    }

    @Override
    public void execute(EcsContext context) {
        context
            .getSingletonComponentByType(CameraComponent.class)
            .ifPresent(this::updateCameraUniforms);
    }

    private void updateCameraUniforms(CameraComponent cameraComponent) {
        java.lang.System.out.println(cameraComponent.getProjectionMatrix());
        cameraUniformBlockFacade.setProjectionMatrix(cameraComponent.getProjectionMatrix());
    }
}
