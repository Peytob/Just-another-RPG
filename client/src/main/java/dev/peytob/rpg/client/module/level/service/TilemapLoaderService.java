package dev.peytob.rpg.client.module.level.service;

import dev.peytob.rpg.core.module.level.model.tilemap.Tilemap;

public interface TilemapLoaderService {

    Tilemap loadTilemap(String tilemapId);
}
