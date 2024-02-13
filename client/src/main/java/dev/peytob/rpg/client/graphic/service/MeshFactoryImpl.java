package dev.peytob.rpg.client.graphic.service;

import dev.peytob.rpg.client.graphic.model.MeshBuilder;
import dev.peytob.rpg.client.graphic.model.render.RenderingQueue;
import dev.peytob.rpg.client.graphic.resource.Mesh;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;

@Service
@RequiredArgsConstructor
public class MeshFactoryImpl implements MeshFactory {

    private final MeshService meshService;

    @Override
    public Mesh buildSpritesMesh(String meshNamePrefix, RenderingQueue renderingQueue) {
        MeshBuilder meshBuilder = MeshBuilder.withCapacitySprites(renderingQueue.size());

        renderingQueue.forEach(renderingSprite ->
            meshBuilder.addSprite(renderingSprite.sprite(), renderingSprite.position(), renderingSprite.renderingSize()));

        renderingQueue.clear();

        ByteBuffer byteBuffer = meshBuilder.getByteBuffer();
        byteBuffer.flip();

        return meshService
            .createMesh(meshNamePrefix, byteBuffer, meshBuilder.getAttributes(), meshBuilder.getVerticesCount());
    }
}
