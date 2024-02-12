package dev.peytob.rpg.core.gameplay.resource.tilemap;

import dev.peytob.rpg.core.gameplay.resource.tilemap.layer.TilemapLayer;
import dev.peytob.rpg.core.gameplay.resource.tilemap.layer.TilemapLayers;
import dev.peytob.rpg.engine.resource.Resource;
import dev.peytob.rpg.math.vector.Vec2i;

import java.util.ArrayList;
import java.util.List;

public class Tilemap implements Resource {

    public static Tilemap create(Vec2i layerSize, int layersCount, String title, String id) {
        if (layersCount < 0) {
            throw new IllegalArgumentException("Layers count cannot be less, than zero");
        }

        ArrayList<TilemapLayer> tilemapLayers = new ArrayList<>(layersCount);
        for (int layerIndex = 0; layerIndex < layersCount; layerIndex++) {
            TilemapLayer layer = TilemapLayers.mutable(layerSize);
            tilemapLayers.add(layer);
        }

        return new Tilemap(tilemapLayers, layerSize, title, id);
    }

    private final List<TilemapLayer> layers;

    private final Vec2i layerSize;

    private final String id;

    private final String title;

    Tilemap(List<TilemapLayer> layers, Vec2i layerSize, String title, String id) {
        this.layers = layers;
        this.layerSize = layerSize;
        this.id = id;
        this.title = title;
    }

    public int getLayersCount() {
        return layers.size();
    }

    public TilemapLayer getMutableLayer(int index) {
        return layers.get(index);
    }

    public String id() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Vec2i getLayerSize() {
        return layerSize;
    }

    public TilemapLayer getUnmodifiableLayer(int index) {
        TilemapLayer tilemapLayer = layers.get(index);
        return TilemapLayers.unmodifiable(tilemapLayer);
    }
}
