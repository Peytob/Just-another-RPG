package dev.peytob.rpg.client.service.graphic;

import dev.peytob.rpg.client.model.graphic.RenderContext;
import dev.peytob.rpg.client.resource.Mesh;
import org.springframework.stereotype.Service;

import static org.lwjgl.opengl.GL33.*;

@Service
public final class OpenGlRenderService implements RenderService {

    @Override
    public void renderMesh(Mesh mesh, RenderContext renderContext) {
        glUseProgram(renderContext.getShaderProgramId());
        glBindVertexArray(mesh.vertexArray().id());
        glDrawArrays(renderContext.getRenderMode(), 0, mesh.verticesCount());
        glBindVertexArray(0);
    }
}
