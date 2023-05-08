package dev.peytob.rpg.client.module.graphic.service.vendor;

import dev.peytob.rpg.client.module.graphic.model.RenderContext;
import dev.peytob.rpg.client.module.graphic.resource.Mesh;
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

    @Override
    public void clearFramebuffer() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}
