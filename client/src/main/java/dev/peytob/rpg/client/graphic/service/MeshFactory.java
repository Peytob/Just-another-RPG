package dev.peytob.rpg.client.graphic.service;

import dev.peytob.rpg.client.graphic.model.render.RenderingQueue;
import dev.peytob.rpg.client.graphic.resource.Mesh;

public interface MeshFactory {

    Mesh buildSpritesMesh(String meshNamePrefix, RenderingQueue renderingQueue);
}
