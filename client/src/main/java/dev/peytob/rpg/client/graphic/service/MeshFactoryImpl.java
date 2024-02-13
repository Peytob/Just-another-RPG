package dev.peytob.rpg.client.graphic.service;

import dev.peytob.rpg.client.graphic.model.MeshBuilder;
import dev.peytob.rpg.client.graphic.model.render.RenderingQueue;
import dev.peytob.rpg.client.graphic.resource.Mesh;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeshFactoryImpl implements MeshFactory {

    private final MeshService meshService;

    @Override
    public Mesh buildSpritesMesh(String meshNamePrefix, RenderingQueue renderingQueue) {
        MeshBuilder meshBuilder = MeshBuilder.withCapacityVertexes(renderingQueue.size());

        renderingQueue.forEach(renderingSprite ->
            meshBuilder.addSprite(renderingSprite.sprite(), renderingSprite.position(), renderingSprite.renderingSize()));

        renderingQueue.clear();

        return meshService
            .createMesh(meshNamePrefix, meshBuilder.getByteBuffer().flip(), meshBuilder.getAttributes(), meshBuilder.getVerticesCount());
    }
}