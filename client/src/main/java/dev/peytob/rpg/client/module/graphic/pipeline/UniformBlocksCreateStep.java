package dev.peytob.rpg.client.module.graphic.pipeline;

import dev.peytob.rpg.client.module.graphic.model.DefaultUniformBlocksData;
import dev.peytob.rpg.client.module.graphic.service.vendor.UniformBlockService;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import org.springframework.stereotype.Component;

@Component
public final class UniformBlocksCreateStep implements InitializingPipelineStep {

    private final UniformBlockService uniformBlockService;

    public UniformBlocksCreateStep(UniformBlockService uniformBlockService) {
        this.uniformBlockService = uniformBlockService;
    }

    @Override
    public void execute() {
        for (var defaultUniformBlockData : DefaultUniformBlocksData.values()) {
            uniformBlockService.createUniformBlock(
                defaultUniformBlockData.getName(),
                defaultUniformBlockData.getSizes(),
                defaultUniformBlockData.getBindingIndex());
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
