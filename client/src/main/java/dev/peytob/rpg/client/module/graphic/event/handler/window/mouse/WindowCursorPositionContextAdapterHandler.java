package dev.peytob.rpg.client.module.graphic.event.handler.window.mouse;

import dev.peytob.rpg.client.module.control.context.event.CursorPositionEvent;
import dev.peytob.rpg.engine.context.EcsContextManager;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public final class WindowCursorPositionContextAdapterHandler {

    private final EcsContextManager ecsContextManager;

    public WindowCursorPositionContextAdapterHandler(EcsContextManager ecsContextManager) {
        this.ecsContextManager = ecsContextManager;
    }

    @EventListener
    public void handleEvent(CursorPositionEvent event) {
        ecsContextManager.addEvent(event);
    }
}
