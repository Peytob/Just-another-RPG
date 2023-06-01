package dev.peytob.rpg.client.module.graphic.context.component;

import dev.peytob.rpg.client.module.graphic.model.RenderableTilemap;
import dev.peytob.rpg.core.module.location.model.tilemap.Tilemap;
import dev.peytob.rpg.ecs.component.SingletonComponent;

public final class TilemapComponent implements SingletonComponent {

    private RenderableTilemap renderableTilemap;

    public TilemapComponent(RenderableTilemap renderableTilemap) {
        this.renderableTilemap = renderableTilemap;
    }

    public Tilemap getTilemap() {
        return this.renderableTilemap.getTilemap();
    }

    public RenderableTilemap getRenderableTilemap() {
        return renderableTilemap;
    }
}
