package dev.peytob.rpg.client.context.system.window;

import dev.peytob.rpg.client.model.graphics.Window;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

@Component
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
