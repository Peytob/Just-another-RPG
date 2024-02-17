package dev.peytob.rpg.client.graphic.service;

import dev.peytob.rpg.client.graphic.model.TexturedMesh;
import dev.peytob.rpg.client.graphic.model.render.RenderingQueue;

public interface MeshFactory {

    TexturedMesh buildSpritesMesh(String meshNamePrefix, RenderingQueue renderingQueue);
}
