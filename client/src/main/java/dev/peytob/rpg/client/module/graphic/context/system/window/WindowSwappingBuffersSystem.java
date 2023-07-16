package dev.peytob.rpg.client.module.graphic.context.system.window;

import dev.peytob.rpg.client.fsm.annotation.IncludeInAllStates;
import dev.peytob.rpg.client.module.graphic.model.Window;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.client.module.graphic.model.RenderSystemDefaultOrder.AFTER_MAIN_RENDERING;

@Component
@IncludeInAllStates(order = AFTER_MAIN_RENDERING)
public final class WindowSwappingBuffersSystem implements System {

    private final Window window;

    public WindowSwappingBuffersSystem(Window window) {
        this.window = window;
    }

    @Override
    public void execute(EcsContext context) {
        window.display();
    }
}
