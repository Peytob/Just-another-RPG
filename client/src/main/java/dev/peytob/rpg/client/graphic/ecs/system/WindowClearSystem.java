package dev.peytob.rpg.client.graphic.ecs.system;

import dev.peytob.rpg.client.fsm.annotation.IncludeInAllStates;
import dev.peytob.rpg.client.graphic.service.RenderService;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.RENDERING;

@IncludeInAllStates(order = RENDERING - 1)
@RequiredArgsConstructor
@Component
public class WindowClearSystem implements System {

    private final RenderService renderService;

    @Override
    public void execute(EcsContext context) {
        renderService.clearWindow();
    }
}
