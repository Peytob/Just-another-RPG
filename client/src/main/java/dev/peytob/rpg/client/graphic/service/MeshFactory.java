package dev.peytob.rpg.client.graphic.service;

import dev.peytob.rpg.client.graphic.model.render.RenderingSprite;
import dev.peytob.rpg.client.graphic.resource.Mesh;

import java.util.Collection;

public interface MeshFactory {

    Mesh buildSpritesMesh(Collection<RenderingSprite> renderingSprites);
}
