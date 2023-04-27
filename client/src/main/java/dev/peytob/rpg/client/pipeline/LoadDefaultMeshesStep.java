package dev.peytob.rpg.client.pipeline;

import dev.peytob.rpg.client.service.graphic.MeshService;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.client.model.resource.graphic.DefaultMeshesIds.TILE_MESH_TEXT_ID;

@Component
public class LoadDefaultMeshesStep implements InitializingPipelineStep {

    private final MeshService meshService;

    public LoadDefaultMeshesStep(MeshService meshService) {
        this.meshService = meshService;
    }

    @Override
    public void execute() {
        meshService.createMesh(new float[] {
            0.0f, 0.0f,
            32.0f, 0.0f,
            0.0f, 32.0f,

            32.0f, 0.0f,
            0.0f, 32.0f,
            32.0f, 32.0f
        }, TILE_MESH_TEXT_ID);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
