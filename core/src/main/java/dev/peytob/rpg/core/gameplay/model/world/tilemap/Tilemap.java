package dev.peytob.rpg.core.gameplay.model.world.tilemap;

import dev.peytob.rpg.core.gameplay.model.world.tilemap.layer.TilemapLayer;
import dev.peytob.rpg.core.gameplay.model.world.tilemap.layer.TilemapLayers;
import dev.peytob.rpg.math.vector.Vec2i;

import java.util.ArrayList;
import java.util.List;

public class Tilemap {

    private final List<TilemapLayer> layers;

    Tilemap(List<TilemapLayer> layers) {
        this.layers = layers;
    }

    public int getLayersSize() {
        return layers.size();
    }

    public TilemapLayer getMutableLayer(int index) {
        return layers.get(index);
    }

    public TilemapLayer getUnmodifiableLayer(int index) {
        TilemapLayer tilemapLayer = layers.get(index);
        return TilemapLayers.unmodifiable(tilemapLayer);
    }

    public static Tilemap create(Vec2i layerSize, int layersCount) {
        if (layersCount < 0) {
            throw new IllegalArgumentException("Layers count cannot be less, than zero");
        }

        ArrayList<TilemapLayer> tilemapLayers = new ArrayList<>(layersCount);
        for (int layerIndex = 0; layerIndex < layersCount; layerIndex++) {
            TilemapLayer layer = TilemapLayers.mutable(layerSize);
            tilemapLayers.set(layerIndex, layer);
        }

        return new Tilemap(tilemapLayers);
    }
}
