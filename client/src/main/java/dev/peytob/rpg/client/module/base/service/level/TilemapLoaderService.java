package dev.peytob.rpg.client.module.base.service.level;

import dev.peytob.rpg.core.module.base.model.level.tilemap.Tilemap;

public interface TilemapLoaderService {

    Tilemap loadTilemap(String tilemapId);
}
