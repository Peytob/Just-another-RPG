package dev.peytob.rpg.client.module.graphic.event.handler.window.mouse.scroll;

import dev.peytob.rpg.client.module.control.context.event.MouseScrollEvent;
import dev.peytob.rpg.ecs.event.Event;
import dev.peytob.rpg.engine.context.EcsContextManager;
import org.springframework.stereotype.Component;

@Component
public final class WindowScrollEventAdapterHandler extends WindowScrollEventHandler {

    private final EcsContextManager ecsContextManager;

    public WindowScrollEventAdapterHandler(EcsContextManager ecsContextManager) {
        this.ecsContextManager = ecsContextManager;
    }

    @Override
    public void handle(double xOffset, double yOffset) {
        Event event = new MouseScrollEvent(xOffset, yOffset);
        ecsContextManager.addEvent(event);
    }
}
