package dev.peytob.rpg.core.gameplay.resource.tilemap.layer;

import dev.peytob.rpg.core.NonContextRpgCoreTest;
import dev.peytob.rpg.core.gameplay.resource.tilemap.PlacedTile;
import dev.peytob.rpg.math.vector.Vec2i;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static dev.peytob.rpg.core.gameplay.resource.TestTiles.*;
import static org.junit.jupiter.api.Assertions.*;

abstract class TilemapLayerTest extends NonContextRpgCoreTest {

    TilemapLayer tilemapLayer;

    abstract TilemapLayer createInstance();

    @BeforeEach
    void createTilemapInstance() {
        tilemapLayer = createInstance();
    }

    @Test
    void isNewTilemapEmpty() {
        eachTile(tilemapLayer, Assertions::assertNull);
    }

    @Test
    void tileCanBeSetFirstTimeCorrectly() {
        int tileX = tilemapLayer.getSizes().x() / 2;
        int tileY = tilemapLayer.getSizes().y() / 3;

        PlacedTile nullTile = tilemapLayer.setTile(tileX, tileY, TEST_TILE_1);
        assertNull(nullTile);

        PlacedTile newTile = tilemapLayer.getTile(tileX, tileY);
        assertEquals(TEST_TILE_1, newTile.tile());
    }

    @Test
    void tileCanBeChangedCorrectly() {
        int tileX = tilemapLayer.getSizes().x() / 3;
        int tileY = tilemapLayer.getSizes().y() / 2;

        tilemapLayer.setTile(tileX, tileY, TEST_TILE_1);

        PlacedTile firstPlacedTile = tilemapLayer.setTile(tileX, tileY, TEST_TILE_2);
        assertEquals(TEST_TILE_1, firstPlacedTile.tile());

        PlacedTile tileAfterChange = tilemapLayer.getTile(tileX, tileY);
        assertEquals(TEST_TILE_2, tileAfterChange.tile());
    }

    @Test
    void negativeCoordinatesReturnsNullTile() {
        PlacedTile tile = Assertions.assertDoesNotThrow(() -> tilemapLayer.getTile(-1, -1));
        assertNull(tile);
    }

    @Test
    void tooBigCoordinatesReturnsNullTile() {
        int tooBigX = tilemapLayer.getSizes().x() + 1;
        int tooBigY = tilemapLayer.getSizes().y() + 1;

        PlacedTile tile = Assertions.assertDoesNotThrow(() -> tilemapLayer.getTile(tooBigX, tooBigY));
        assertNull(tile);
    }

    @Test
    void removingTileWorkingCurrently() {
        Vec2i tileCoordinates = tilemapLayer.getSizes().minus(1, 1);

        tilemapLayer.setTile(tileCoordinates, TEST_TILE_3);
        tilemapLayer.removeTile(tileCoordinates);
        PlacedTile tile = tilemapLayer.getTile(tileCoordinates);
        assertNull(tile);
    }

    private void eachTile(TilemapLayer tilemapLayer, Consumer<PlacedTile> tileConsumer) {
        Vec2i tilemapSizes = tilemapLayer.getSizes();

        for (int x = 0; x < tilemapSizes.x(); x++) {
            for (int y = 0; y < tilemapSizes.y(); y++) {
                PlacedTile tile = tilemapLayer.getTile(x, y);
                tileConsumer.accept(tile);
            }
        }
    }
}
