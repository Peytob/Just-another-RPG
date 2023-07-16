package dev.peytob.rpg.client.module.graphic.context.system.window;

import dev.peytob.rpg.client.fsm.annotation.IncludeInAllStates;
import dev.peytob.rpg.client.module.graphic.model.Window;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.UPDATE_POOLING;

@Component
@IncludeInAllStates(order = UPDATE_POOLING)
public final class WindowEventPoolingSystem implements System {

    private final Window window;

    public WindowEventPoolingSystem(Window window) {
        this.window = window;
    }

    @Override
    public void execute(EcsContext context) {
        window.pollEvents();
    }
}
