package dev.peytob.rpg.client.module.graphic.service.facade;

import dev.peytob.rpg.client.module.graphic.model.TextureAtlas;
import dev.peytob.rpg.client.module.graphic.resource.Mesh;
import dev.peytob.rpg.core.module.location.model.tilemap.Tilemap;
import dev.peytob.rpg.math.geometry.RectI;

public interface TilemapMeshService {

    Mesh buildTilemapMesh(String textId, Tilemap tilemap, TextureAtlas textureAtlas, RectI cullingTilesRect);
}
