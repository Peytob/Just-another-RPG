package dev.peytob.rpg.core.module.location.context.component;

import dev.peytob.rpg.core.module.location.model.tilemap.Tilemap;
import dev.peytob.rpg.ecs.component.SingletonComponent;

public class TilemapComponent implements SingletonComponent {

    private Tilemap tilemap;

    public TilemapComponent(Tilemap tilemap) {
        this.tilemap = tilemap;
    }

    public Tilemap getTilemap() {
        return tilemap;
    }

    public void setTilemap(Tilemap tilemap) {
        this.tilemap = tilemap;
    }
}
