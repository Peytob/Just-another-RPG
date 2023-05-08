package dev.peytob.rpg.client.context.component.level;

import dev.peytob.rpg.core.module.location.model.tilemap.Tilemap;
import dev.peytob.rpg.ecs.component.Component;

public class TilemapComponent implements Component {

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
