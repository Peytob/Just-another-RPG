package dev.peytob.rpg.client.graphic.service;

import dev.peytob.rpg.client.graphic.model.TexturedMesh;
import dev.peytob.rpg.client.graphic.model.opengl.RenderMode;
import dev.peytob.rpg.client.graphic.model.render.RenderingContext;
import dev.peytob.rpg.client.graphic.model.render.RenderingQueue;
import dev.peytob.rpg.client.graphic.resource.Mesh;
import dev.peytob.rpg.client.graphic.resource.Texture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;
import static org.lwjgl.opengl.GL33.*;

@Service
@RequiredArgsConstructor
public class RenderService {

    private final MeshFactory meshFactory;

    private final MeshService meshService;

    public void performRendering(RenderingContext renderingContext, RenderingQueue renderingQueue) {
        if (renderingQueue.isEmpty()) {
            return;
        }

        requireNonNull(renderingContext.getShaderProgram(), "Rendering shader program is not set in context");
        glUseProgram(renderingContext.getShaderProgram().vendorId());

        String meshNamePrefix = requireNonNullElse(renderingContext.getRenderingMeshName(), "temp_render");

        TexturedMesh texturedMesh = meshFactory.buildSpritesMesh(meshNamePrefix, renderingQueue);

        setUpTextures(texturedMesh);
        renderMesh(texturedMesh.getMesh(), renderingContext.getRenderMode());

        meshService.removeMesh(texturedMesh.getMesh());
    }

    private void setUpTextures(TexturedMesh texturedMesh) {
        for (Texture texture : texturedMesh.getUsedTextures()) {
            int textureIndex = texturedMesh.getTextureIndex(texture);
            glActiveTexture(GL_TEXTURE0 + textureIndex);
            glBindTexture(GL_TEXTURE_2D, texture.vendorId());
        }
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
