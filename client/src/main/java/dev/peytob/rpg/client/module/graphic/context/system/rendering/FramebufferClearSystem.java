package dev.peytob.rpg.client.module.graphic.context.system.rendering;

import dev.peytob.rpg.client.fsm.annotation.IncludeInAllStates;
import dev.peytob.rpg.client.module.graphic.service.vendor.RenderService;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.client.module.graphic.model.RenderSystemDefaultOrder.BEFORE_MAIN_RENDERING;

@Component
@IncludeInAllStates(order = BEFORE_MAIN_RENDERING)
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
