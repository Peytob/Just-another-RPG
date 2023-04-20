package dev.peytob.rpg.client.context.system.graphic.rendering;

import dev.peytob.rpg.client.context.component.graphic.MeshComponent;
import dev.peytob.rpg.client.model.graphic.RenderContext;
import dev.peytob.rpg.client.resource.Mesh;
import dev.peytob.rpg.client.resource.ShaderProgram;
import dev.peytob.rpg.client.service.graphic.DefaultShaderProgramsService;
import dev.peytob.rpg.client.service.graphic.MeshService;
import dev.peytob.rpg.client.service.graphic.RenderService;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public final class MeshRenderingSystem implements System {

    private final RenderService renderService;

    private final DefaultShaderProgramsService defaultShaderProgramsService;

    private final MeshService meshService;

    public MeshRenderingSystem(RenderService renderService, DefaultShaderProgramsService defaultShaderProgramsService, MeshService meshService) {
        this.renderService = renderService;
        this.defaultShaderProgramsService = defaultShaderProgramsService;
        this.meshService = meshService;
    }

    @Override
    public void execute(EcsContext context) {
        Collection<MeshComponent> meshes = context.getComponentsByType(MeshComponent.class);

        ShaderProgram shaderProgram = defaultShaderProgramsService.getTilemapShaderProgram();

        RenderContext renderContext = new RenderContext();
        renderContext.setShaderProgramId(shaderProgram.id());

        meshes.forEach(meshComponent -> {
            Mesh mesh = meshService.getMeshById(meshComponent.getMeshId());
            renderService.renderMesh(mesh, renderContext);
        });
    }
}
