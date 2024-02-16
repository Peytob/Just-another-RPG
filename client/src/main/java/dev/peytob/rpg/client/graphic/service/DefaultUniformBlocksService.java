package dev.peytob.rpg.client.graphic.service;

import dev.peytob.rpg.client.graphic.model.Camera;
import dev.peytob.rpg.client.graphic.model.DefaultUniformBlocks;
import dev.peytob.rpg.client.graphic.resource.UniformBlock;
import dev.peytob.rpg.math.matrix.Mat4;
import dev.peytob.rpg.math.vector.Vec2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static dev.peytob.rpg.client.graphic.model.RenderingConstants.RENDERING_COEFFICIENT;
import static dev.peytob.rpg.math.matrix.Matrices.ortho2D;
import static org.lwjgl.opengl.GL33.*;

@Service
@RequiredArgsConstructor
public class DefaultUniformBlocksService {

    private final UniformBlockService uniformBlockService;

    /**
     * Facade for interact with this uniforms block:
     * layout (std140) uniform Camera {
     *     mat4 projection;
     * };
     */
    public void updateCameraUniformBlock(Camera camera) {
        getUniformBlock(DefaultUniformBlocks.CAMERA).ifPresent(uniformBlock ->
            updateCameraProjectionMatrix(uniformBlock, camera)
        );
    }

    private void updateCameraProjectionMatrix(UniformBlock cameraUniformBlock, Camera camera) {
        Mat4 projectionMatrix = computeCameraOrthoMatrix(camera);

        glBindBuffer(GL_UNIFORM_BUFFER, cameraUniformBlock.buffer().vendorId());

        glBufferSubData(GL_UNIFORM_BUFFER, 0, projectionMatrix.getRaw());
        glBindBuffer(GL_UNIFORM_BUFFER, 0);
    }

    private Mat4 computeCameraOrthoMatrix(Camera camera) {
        Vec2 topLeftScreenPoint = camera.getPosition().multiply(RENDERING_COEFFICIENT);
        Vec2 rightBottomScreenPoint = camera.getPosition().plus(camera.getResolution()).multiply(RENDERING_COEFFICIENT);

        return ortho2D(
            topLeftScreenPoint.x(),
            rightBottomScreenPoint.x(),
            rightBottomScreenPoint.y(),
            topLeftScreenPoint.y());
    }

    private Optional<UniformBlock> getUniformBlock(DefaultUniformBlocks defaultUniformBlock) {
        return uniformBlockService.getUniformBlock(defaultUniformBlock.getName());
    }
}
