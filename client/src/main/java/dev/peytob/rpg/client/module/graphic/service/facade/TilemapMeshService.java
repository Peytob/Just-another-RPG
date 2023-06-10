package dev.peytob.rpg.client.module.graphic.service.facade;

import dev.peytob.rpg.client.module.graphic.resource.Mesh;
import dev.peytob.rpg.core.module.location.model.tilemap.Tilemap;

public interface TilemapMeshService {

    Mesh buildTilemapMesh(Tilemap tilemap);
}
