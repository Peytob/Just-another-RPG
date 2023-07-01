package dev.peytob.rpg.client.module.graphic.event.handler.window.mouse;

import dev.peytob.rpg.client.module.control.context.event.MouseButtonEvent;
import dev.peytob.rpg.engine.context.EcsContextManager;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public final class WindowMouseButtonContextAdapterHandler {

    private final EcsContextManager ecsContextManager;

    public WindowMouseButtonContextAdapterHandler(EcsContextManager ecsContextManager) {
        this.ecsContextManager = ecsContextManager;
    }

    @EventListener
    public void handleEvent(MouseButtonEvent event) {
        ecsContextManager.addEvent(event);
    }
}
