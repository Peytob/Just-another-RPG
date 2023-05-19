package dev.peytob.rpg.client.module.graphic.event.handler.window.key;

import dev.peytob.rpg.client.module.control.context.event.KeyboardKeyEvent;
import dev.peytob.rpg.engine.context.EcsContextManager;
import dev.peytob.rpg.engine.event.EventHandler;
import org.springframework.stereotype.Component;

@Component
public final class WindowKeyContextAdapterHandler implements EventHandler<KeyboardKeyEvent> {

    private final EcsContextManager ecsContextManager;

    public WindowKeyContextAdapterHandler(EcsContextManager ecsContextManager) {
        this.ecsContextManager = ecsContextManager;
    }

    @Override
    public void handleEvent(KeyboardKeyEvent event) {
        ecsContextManager.addEvent(event);
    }
}
