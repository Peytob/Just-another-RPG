package dev.peytob.rpg.client.graphic.model;

import dev.peytob.rpg.client.graphic.resource.Mesh;
import dev.peytob.rpg.client.graphic.resource.Texture;

import java.util.Collection;
import java.util.Map;

public class TexturedMesh {

    private final Mesh mesh;

    private final Map<Texture, Integer> textureIndexes;

    public TexturedMesh(Mesh mesh, Map<Texture, Integer> textureIndexes) {
        this.mesh = mesh;
        this.textureIndexes = textureIndexes;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Collection<Texture> getUsedTextures() {
        return textureIndexes.keySet();
    }

    public int getTextureIndex(Texture texture) {
        return textureIndexes.get(texture);
    }
}
