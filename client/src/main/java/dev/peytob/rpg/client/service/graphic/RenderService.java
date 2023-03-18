package dev.peytob.rpg.client.service.graphic;

import dev.peytob.rpg.client.model.graphic.RenderContext;
import dev.peytob.rpg.client.resource.Mesh;

public interface RenderService {

    void renderMesh(Mesh mesh, RenderContext renderContext);
}
