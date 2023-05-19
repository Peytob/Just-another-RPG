package dev.peytob.rpg.client.module.graphic.context.system.window;

import dev.peytob.rpg.client.module.graphic.context.event.window.WindowCloseEvent;
import dev.peytob.rpg.client.module.graphic.model.Window;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

@Component
public final class WindowClosingHandlingSystem implements System {

    private final Window window;

    public WindowClosingHandlingSystem(Window window) {
        this.window = window;
    }

    @Override
    public void execute(EcsContext context) {
        boolean isWindowCloseEventPresented = !context.getEventsByType(WindowCloseEvent.class).isEmpty();

        if (isWindowCloseEventPresented) {
            window.close();
        }
    }
}
