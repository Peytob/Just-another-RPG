package dev.peytob.rpg.client.module.graphic.repository;

import dev.peytob.rpg.client.module.graphic.resource.Texture;
import dev.peytob.rpg.client.module.graphic.resource.TextureAtlas;
import dev.peytob.rpg.engine.repositry.BaseRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class TextureAtlasRepository extends BaseRepository<TextureAtlas> {

    private final RepositoryIndex<Integer> textureIndex;

    public TextureAtlasRepository() {
        this.textureIndex = this.registerIndex(textureAtlas -> textureAtlas.texture().id());
    }

    public Collection<TextureAtlas> getAtlasesByTexture(Texture texture) {
        return textureIndex.get(texture.id());
    }
}
