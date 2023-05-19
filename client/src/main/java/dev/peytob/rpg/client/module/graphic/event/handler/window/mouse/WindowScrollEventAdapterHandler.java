package dev.peytob.rpg.client.module.graphic.event.handler.window.mouse;

import dev.peytob.rpg.client.module.control.context.event.MouseScrollEvent;
import dev.peytob.rpg.engine.context.EcsContextManager;
import dev.peytob.rpg.engine.event.EventHandler;
import org.springframework.stereotype.Component;

@Component
public final class WindowScrollEventAdapterHandler implements EventHandler<MouseScrollEvent> {

    private final EcsContextManager ecsContextManager;

    public WindowScrollEventAdapterHandler(EcsContextManager ecsContextManager) {
        this.ecsContextManager = ecsContextManager;
    }

    @Override
    public void handleEvent(MouseScrollEvent event) {
        ecsContextManager.addEvent(event);
    }
}
