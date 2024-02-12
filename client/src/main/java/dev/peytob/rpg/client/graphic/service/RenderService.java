package dev.peytob.rpg.client.graphic.service;

import dev.peytob.rpg.client.graphic.model.glfw.Window;
import dev.peytob.rpg.client.graphic.model.opengl.RenderMode;
import dev.peytob.rpg.client.graphic.model.render.RenderingContext;
import dev.peytob.rpg.client.graphic.model.render.RenderingQueue;
import dev.peytob.rpg.client.graphic.resource.Mesh;
import dev.peytob.rpg.client.graphic.resource.VertexArray;
import lombok.RequiredArgsConstructor;
import org.lwjgl.BufferUtils;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.lwjgl.opengl.GL33.*;

@Service
@RequiredArgsConstructor
public class RenderService {

    private final MeshFactory meshFactory;

    private final MeshService meshService;

    private final Window window;

    public void performRendering(RenderingContext renderingContext, RenderingQueue renderingQueue) {
//        if (renderingQueue.isEmpty()) {
//            return;
//        }

        requireNonNull(renderingContext.getShaderProgram(), "Rendering shader program is not set in context");
        glUseProgram(renderingContext.getShaderProgram().vendorId());

        ByteBuffer allocate = BufferUtils.createByteBuffer(1024)
            .putFloat(-0.5f).putFloat(-0.5f)
            .putFloat(0.5f).putFloat(-0.5f)
            .putFloat(0f).putFloat(0.5f)
            .flip();

        Collection<VertexArray.VertexArrayAttribute> vertexArrayAttributes = List.of(
            new VertexArray.VertexArrayAttribute(0, 2, GL_FLOAT, false, 2 * Float.BYTES, 0L)
        );

        Mesh mesh = meshService.createMesh("tilemap_frame_mesh", allocate, vertexArrayAttributes, 3);

        renderMesh(mesh, renderingContext.getRenderMode());

        meshService.removeMesh(mesh);
    }

    private void renderMesh(Mesh mesh, RenderMode renderMode) {
        glBindVertexArray(mesh.vertexArray().vendorId());
        glDrawArrays(renderMode.getGlValue(), 0, mesh.verticesCount());
        glBindVertexArray(0);
    }

    public void clearWindow() {
        glClear(GL_COLOR_BUFFER_BIT);
    }
}
