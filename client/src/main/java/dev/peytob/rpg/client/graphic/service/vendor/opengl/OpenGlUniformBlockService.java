package dev.peytob.rpg.client.graphic.service.vendor.opengl;

import dev.peytob.rpg.client.graphic.model.opengl.BufferTarget;
import dev.peytob.rpg.client.graphic.repository.UniformBlockRepository;
import dev.peytob.rpg.client.graphic.resource.GraphicBuffer;
import dev.peytob.rpg.client.graphic.resource.UniformBlock;
import dev.peytob.rpg.client.graphic.service.GraphicBufferService;
import dev.peytob.rpg.client.graphic.service.UniformBlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.lwjgl.opengl.GL33.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class OpenGlUniformBlockService implements UniformBlockService {

    private final UniformBlockRepository uniformBlockRepository;

    private final GraphicBufferService graphicBufferService;

    @Override
    public UniformBlock createUniformBlock(String uniformBlockId, int sizeBytes, int bindingIndex) {
        log.info("Creating new uniform block with id {}", uniformBlockId);

        GraphicBuffer graphicBuffer = graphicBufferService.createBuffer(uniformBlockId + "_buffer", BufferTarget.UNIFORM_BUFFER);

        UniformBlock uniformBlock = new UniformBlock(
            uniformBlockId,
            sizeBytes,
            bindingIndex,
            graphicBuffer);

        glBindBuffer(GL_UNIFORM_BUFFER, graphicBuffer.vendorId());
        glBufferData(GL_UNIFORM_BUFFER, sizeBytes, GL_STATIC_DRAW);
        glBindBuffer(GL_UNIFORM_BUFFER, 0);

        glBindBufferRange(GL_UNIFORM_BUFFER, bindingIndex, graphicBuffer.vendorId(), 0, sizeBytes);

        uniformBlockRepository.append(uniformBlock);

        log.info("Created new uniform block with id {}. Uniform buffer vendor id: {}", uniformBlockId, uniformBlock.buffer().vendorId());

        return uniformBlock;
    }

    @Override
    public boolean removeUniformBlock(UniformBlock uniformBlock) {
        log.info("Removing uniform block with id {}", uniformBlock.id());

        glBindBuffer(GL_UNIFORM_BUFFER, 0);

        if (!uniformBlockRepository.contains(uniformBlock.id())) {
            log.warn("Uniform block with id {} not found during deletion", uniformBlock.id());
            return false;
        }

        uniformBlockRepository.remove(uniformBlock);
        graphicBufferService.removeBuffer(uniformBlock.buffer());

        return true;
    }

    @Override
    public Optional<UniformBlock> getUniformBlock(String uniformBlockId) {
        return uniformBlockRepository.getById(uniformBlockId);
    }
}
