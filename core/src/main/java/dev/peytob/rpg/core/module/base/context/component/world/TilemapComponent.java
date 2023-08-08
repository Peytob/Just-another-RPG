package dev.peytob.rpg.core.module.base.context.component.world;

import dev.peytob.rpg.core.module.base.model.world.tilemap.Tilemap;
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
