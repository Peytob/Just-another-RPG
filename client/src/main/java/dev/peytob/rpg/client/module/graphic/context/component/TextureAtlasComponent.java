package dev.peytob.rpg.client.module.graphic.context.component;

import dev.peytob.rpg.client.module.graphic.model.TextureAtlas;
import dev.peytob.rpg.ecs.component.Component;

public class TextureAtlasComponent implements Component {

    private final TextureAtlas textureAtlas;

    public TextureAtlasComponent(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }
}
