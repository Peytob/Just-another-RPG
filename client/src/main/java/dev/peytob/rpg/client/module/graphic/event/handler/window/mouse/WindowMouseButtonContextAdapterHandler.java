package dev.peytob.rpg.client.module.graphic.event.handler.window.mouse;

import dev.peytob.rpg.client.module.control.context.event.MouseButtonEvent;
import dev.peytob.rpg.engine.context.EcsContextManager;
import dev.peytob.rpg.engine.event.EventHandler;
import org.springframework.stereotype.Component;

@Component
public final class WindowMouseButtonContextAdapterHandler implements EventHandler<MouseButtonEvent> {

    private final EcsContextManager ecsContextManager;

    public WindowMouseButtonContextAdapterHandler(EcsContextManager ecsContextManager) {
        this.ecsContextManager = ecsContextManager;
    }

    @Override
    public void handleEvent(MouseButtonEvent event) {
        ecsContextManager.addEvent(event);
    }
}
