package dev.peytob.rpg.core.module.base.model.world.tilemap;

import dev.peytob.rpg.math.vector.Vectors;

public class ArrayTilemapTest extends TilemapTest {

    @Override
    Tilemap createInstance() {
        return new ArrayTilemap(Vectors.immutableVec2i(16, 16));
    }
}
