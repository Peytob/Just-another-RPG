package dev.peytob.rpg.client.graphic.model.render;

import dev.peytob.rpg.client.graphic.model.Camera;
import dev.peytob.rpg.client.graphic.model.opengl.RenderMode;
import dev.peytob.rpg.client.graphic.resource.ShaderProgram;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RenderingContext {

    private Camera camera;

    private ShaderProgram shaderProgram;

    @Builder.Default
    private RenderMode renderMode = RenderMode.TRIANGLES;

    private String renderingMeshName;
}
