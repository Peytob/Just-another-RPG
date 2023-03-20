package dev.peytob.rpg.client.context.system.graphic.rendering;

import dev.peytob.rpg.client.service.graphic.RenderService;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

@Component
public final class FramebufferClearSystem implements System {

    private final RenderService renderService;

    public FramebufferClearSystem(RenderService renderService) {
        this.renderService = renderService;
    }

    @Override
    public void execute(EcsContext context) {
        renderService.clearFramebuffer();
    }
}
