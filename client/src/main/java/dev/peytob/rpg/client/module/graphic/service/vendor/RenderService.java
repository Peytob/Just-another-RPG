package dev.peytob.rpg.client.module.graphic.service.vendor;

import dev.peytob.rpg.client.module.graphic.model.RenderContext;
import dev.peytob.rpg.client.module.graphic.resource.Mesh;

public interface RenderService {

    void renderMesh(Mesh mesh, RenderContext renderContext);

    void clearFramebuffer();
}
