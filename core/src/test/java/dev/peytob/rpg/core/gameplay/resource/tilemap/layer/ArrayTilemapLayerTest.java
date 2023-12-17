package dev.peytob.rpg.core.gameplay.resource.tilemap.layer;

import dev.peytob.rpg.math.vector.Vectors;

public class ArrayTilemapLayerTest extends TilemapLayerTest {

    @Override
    TilemapLayer createInstance() {
        return new ArrayTilemapLayer(Vectors.immutableVec2i(16, 16));
    }
}
