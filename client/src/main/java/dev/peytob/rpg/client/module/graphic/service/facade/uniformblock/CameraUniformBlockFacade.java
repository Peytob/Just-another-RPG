package dev.peytob.rpg.client.module.graphic.service.facade.uniformblock;

import dev.peytob.rpg.math.matrix.Mat4;
import dev.peytob.rpg.math.vector.Vec2i;
import org.springframework.stereotype.Component;

@Component
public interface CameraUniformBlockFacade {

    void setProjectionMatrix(Mat4 matrix);

    void setScreenResolution(Vec2i resolution);
}
