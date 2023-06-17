package dev.peytob.rpg.client.module.graphic.service.facade;

import dev.peytob.rpg.client.module.graphic.model.RenderableTilemap;
import dev.peytob.rpg.client.module.graphic.resource.Mesh;

public interface TilemapMeshService {

    Mesh buildTilemapMesh(String textId, RenderableTilemap renderableTilemap);
}
