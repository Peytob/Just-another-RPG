package dev.peytob.rpg.client.graphic.service;

import dev.peytob.rpg.client.graphic.model.render.RenderingSprite;
import dev.peytob.rpg.client.graphic.resource.Mesh;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MeshFactoryImpl implements MeshFactory {

    @Override
    public Mesh buildSpritesMesh(Collection<RenderingSprite> renderingSprites) {
        return null;
    }
}
