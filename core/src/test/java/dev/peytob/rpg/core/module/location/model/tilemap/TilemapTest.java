package dev.peytob.rpg.core.module.location.model.tilemap;

import dev.peytob.rpg.core.NonContextRpgCoreTest;
import dev.peytob.rpg.core.module.location.resource.Tile;
import dev.peytob.rpg.math.vector.Vec2i;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static dev.peytob.rpg.core.resource.TestTiles.*;
import static org.junit.jupiter.api.Assertions.*;

abstract class TilemapTest extends NonContextRpgCoreTest {

    Tilemap tilemap;

    abstract Tilemap createInstance();

    @BeforeEach
    void createTilemapInstance() {
        tilemap = createInstance();
    }

    @Test
    void isNewTilemapEmpty() {
        eachTile(tilemap, Assertions::assertNull);
    }

    @Test
    void tileCanBeSetFirstTimeCorrectly() {
        int tileX = tilemap.getSizes().x() / 2;
        int tileY = tilemap.getSizes().y() / 3;

        Tile nullTile = tilemap.setTile(tileX, tileY, TEST_TILE_1);
        assertNull(nullTile);

        Tile newTile = tilemap.getTile(tileX, tileY);
        assertEquals(TEST_TILE_1, newTile);
    }

    @Test
    void tileCanBeChangedCorrectly() {
        int tileX = tilemap.getSizes().x() / 3;
        int tileY = tilemap.getSizes().y() / 2;

        tilemap.setTile(tileX, tileY, TEST_TILE_1);

        Tile firstPlacedTile = tilemap.setTile(tileX, tileY, TEST_TILE_2);
        assertEquals(TEST_TILE_1, firstPlacedTile);

        Tile tileAfterChange = tilemap.getTile(tileX, tileY);
        assertEquals(TEST_TILE_2, tileAfterChange);
    }

    @Test
    void negativeCoordinatesReturnsNullTile() {
        Tile tile = assertDoesNotThrow(() -> tilemap.getTile(-1, -1));
        assertNull(tile);
    }

    @Test
    void tooBigCoordinatesReturnsNullTile() {
        int tooBigX = tilemap.getSizes().x() + 1;
        int tooBigY = tilemap.getSizes().y() + 1;

        Tile tile = assertDoesNotThrow(() -> tilemap.getTile(tooBigX, tooBigY));
        assertNull(tile);
    }

    @Test
    void removingTileWorkingCurrently() {
        Vec2i tileCoordinates = tilemap.getSizes().minus(1, 1);

        tilemap.setTile(tileCoordinates, TEST_TILE_3);
        tilemap.removeTile(tileCoordinates);
        Tile tile = tilemap.getTile(tileCoordinates);
        assertNull(tile);
    }

    private void eachTile(Tilemap tilemap, Consumer<Tile> tileConsumer) {
        Vec2i tilemapSizes = tilemap.getSizes();

        for (int x = 0; x < tilemapSizes.x(); x++) {
            for (int y = 0; y < tilemapSizes.y(); y++) {
                Tile tile = tilemap.getTile(x, y);
                tileConsumer.accept(tile);
            }
        }
    }
}