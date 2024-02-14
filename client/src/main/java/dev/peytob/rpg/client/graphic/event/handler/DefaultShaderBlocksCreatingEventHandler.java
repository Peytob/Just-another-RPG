package dev.peytob.rpg.client.graphic.event.handler;

import dev.peytob.rpg.client.fsm.event.instance.AfterEngineStatePushEvent;
import dev.peytob.rpg.client.graphic.model.DefaultUniformBlocks;
import dev.peytob.rpg.client.graphic.service.UniformBlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultShaderBlocksCreatingEventHandler {

    private final UniformBlockService uniformBlockService;

    @EventListener
    public void execute(AfterEngineStatePushEvent ignored) {
        for (var defaultUniformBlockData : DefaultUniformBlocks.values()) {
            uniformBlockService.createUniformBlock(
                defaultUniformBlockData.getName(),
                defaultUniformBlockData.getSizes(),
                defaultUniformBlockData.getBindingIndex());
        }
    }
}
