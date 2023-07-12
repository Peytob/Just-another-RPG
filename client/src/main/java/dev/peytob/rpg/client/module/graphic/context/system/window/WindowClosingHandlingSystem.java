package dev.peytob.rpg.client.module.graphic.context.system.window;

import dev.peytob.rpg.client.fsm.annotation.IncludeInAllStates;
import dev.peytob.rpg.client.module.graphic.context.event.window.WindowCloseEvent;
import dev.peytob.rpg.client.module.graphic.model.Window;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.AFTER_ALL;

@Component
@IncludeInAllStates(order = AFTER_ALL)
public final class WindowClosingHandlingSystem implements System {

    private final Window window;

    public WindowClosingHandlingSystem(Window window) {
        this.window = window;
    }

    @Override
    public void execute(EcsContext context) {
        boolean isWindowCloseEventPresented = !context.getEventsByType(WindowCloseEvent.class).isEmpty();

        if (isWindowCloseEventPresented) {
            window.setCloseFlag(true);
        }
    }
}
