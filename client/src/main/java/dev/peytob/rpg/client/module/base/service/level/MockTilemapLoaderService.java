package dev.peytob.rpg.client.module.base.service.level;

import dev.peytob.rpg.core.module.base.model.level.tilemap.Tilemap;
import dev.peytob.rpg.core.module.base.model.level.tilemap.Tilemaps;
import dev.peytob.rpg.core.module.base.resource.Tile;
import org.springframework.stereotype.Service;

import java.util.Random;

import static dev.peytob.rpg.math.vector.Vectors.immutableVec2i;

@Service
public class MockTilemapLoaderService implements TilemapLoaderService {

    @Override
    public Tilemap loadTilemap(String tilemapId) {
        Tilemap tilemap = Tilemaps.mutable(immutableVec2i(16, 16));
        Tile[] mockTiles = new Tile[] {
            new Tile(1, "blue_tile"),
            new Tile(2, "red_tile"),
            new Tile(3, "pink_tile"),
            new Tile(4, "green_tile")
        };
        Random random = new Random();

        for (int x = 0; x < tilemap.getSizes().x(); x++) {
            for (int y = 0; y < tilemap.getSizes().y(); y++) {
                int mockTileIndex = random.nextInt(mockTiles.length);
                tilemap.setTile(x, y, mockTiles[mockTileIndex]);
            }
        }

        try {
            // Imagine, that its hard work for couple seconds...
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException("Oh...", e);
        }
        return tilemap;
    }
}
