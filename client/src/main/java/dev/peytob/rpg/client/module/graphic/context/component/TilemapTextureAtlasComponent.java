package dev.peytob.rpg.client.module.graphic.context.component;

import dev.peytob.rpg.client.module.graphic.model.TextureAtlas;
import dev.peytob.rpg.ecs.component.SingletonComponent;

public class TilemapTextureAtlasComponent implements SingletonComponent {

    private TextureAtlas textureAtlas;

    public TilemapTextureAtlasComponent(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    public void setTextureAtlas(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
    }
}
