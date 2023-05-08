package dev.peytob.rpg.client.module.graphic.service.facade;

import dev.peytob.rpg.client.module.graphic.resource.Mesh;
import dev.peytob.rpg.client.module.graphic.service.MeshService;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.client.module.graphic.model.DefaultMeshesIds.TILE_MESH_TEXT_ID;

@Component
public class DefaultMeshesFacadeService implements DefaultMeshesService {

    private final MeshService meshService;

    public DefaultMeshesFacadeService(MeshService meshService) {
        this.meshService = meshService;
    }

    @Override
    public Mesh getTileMesh() {
        return meshService.getMeshByTextId(TILE_MESH_TEXT_ID);
    }
}
