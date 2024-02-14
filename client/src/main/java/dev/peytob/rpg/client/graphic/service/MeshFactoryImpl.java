package dev.peytob.rpg.client.graphic.service;

import dev.peytob.rpg.client.graphic.model.MeshBuilder;
import dev.peytob.rpg.client.graphic.model.render.RenderingQueue;
import dev.peytob.rpg.client.graphic.resource.Mesh;
import lombok.RequiredArgsConstructor;
import org.lwjgl.BufferUtils;
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

//        ByteBuffer byteBuffer = meshBuilder.getByteBuffer();
//        byteBuffer.flip();

        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(1024)
            .putFloat(150).putFloat(100)
            .putFloat(0.5f).putFloat(0.5f)
            .putInt(1)

            .putFloat(500).putFloat(500)
            .putFloat(1).putFloat(2)
            .putInt(1)

            .putFloat(700).putFloat(50)
            .putFloat(1).putFloat(2)
            .putInt(1)

            .flip();

//        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(1024)
//            .putFloat( 0.5f).putFloat( -0.5f)
//            .putFloat( 0.5f).putFloat( 0.5f)
//            .putInt(1)
//
//            .putFloat( 0f).putFloat( 0.5f)
//            .putFloat(1).putFloat(2)
//            .putInt(1)
//
//            .putFloat( -0.5f).putFloat( 0.5f)
//            .putFloat(1).putFloat(2)
//            .putInt(1)
//
//            .flip();

        return meshService
            .createMesh(meshNamePrefix, byteBuffer, meshBuilder.getAttributes(), 3);
    }
}
