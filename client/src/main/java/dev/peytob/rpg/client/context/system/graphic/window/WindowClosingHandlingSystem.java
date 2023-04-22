package dev.peytob.rpg.client.context.system.graphic.window;

import dev.peytob.rpg.client.context.event.window.WindowCloseEvent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import dev.peytob.rpg.engine.RpgEngine;
import org.springframework.stereotype.Component;

@Component
public class WindowClosingHandlingSystem implements System {

    private final RpgEngine rpgEngine;

    public WindowClosingHandlingSystem(RpgEngine rpgEngine) {
        this.rpgEngine = rpgEngine;
    }

    @Override
    public void execute(EcsContext context) {
        boolean isWindowCloseEventPresented = !context.getEventsByType(WindowCloseEvent.class).isEmpty();

        if (isWindowCloseEventPresented) {
            rpgEngine.close();
        }
    }
}
