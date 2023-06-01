package dev.peytob.rpg.client.module.graphic.service.facade.uniformblock;

import dev.peytob.rpg.client.module.graphic.model.DefaultUniformBlocksData;
import dev.peytob.rpg.client.module.graphic.repository.UniformBlockRepository;
import dev.peytob.rpg.client.module.graphic.resource.UniformBlock;
import dev.peytob.rpg.math.matrix.Mat4;
import dev.peytob.rpg.math.vector.Vec2i;
import org.springframework.stereotype.Component;

import static org.lwjgl.opengl.GL31.*;

/**
 * Facade for interact with this uniforms block:
 * layout (std140) uniform Camera {
 *     mat4 projection;
 *     ivec2 resolution;
 * };
 */
@Component
public class CameraUniformBlockFacadeImpl implements CameraUniformBlockFacade {

    private final UniformBlockRepository uniformBlockRepository;

    public CameraUniformBlockFacadeImpl(UniformBlockRepository uniformBlockRepository) {
        this.uniformBlockRepository = uniformBlockRepository;
    }

    @Override
    public void setProjectionMatrix(Mat4 matrix) {
        UniformBlock uniformBlock = getUniformBlock();

        glBindBuffer(GL_UNIFORM_BUFFER, uniformBlock.buffer().id());
        glBufferSubData(GL_UNIFORM_BUFFER, 0, matrix.getRaw());
        glBindBuffer(GL_UNIFORM_BUFFER, 0);
    }

    @Override
    public void setScreenResolution(Vec2i resolution) {
        UniformBlock uniformBlock = getUniformBlock();

        glBindBuffer(GL_UNIFORM_BUFFER, uniformBlock.buffer().id());
        glBufferSubData(GL_UNIFORM_BUFFER, Mat4.BYTES, new int[] { resolution.x(), resolution.y() });
        glBindBuffer(GL_UNIFORM_BUFFER, 0);
    }

    private UniformBlock getUniformBlock() {
        return uniformBlockRepository.getById(DefaultUniformBlocksData.CAMERA.getName());
    }
}
