package dev.peytob.rpg.client.module.graphic.event.handler.window.mouse.position;

import dev.peytob.rpg.client.context.event.input.CursorPositionEvent;
import dev.peytob.rpg.client.module.graphic.model.Window;
import dev.peytob.rpg.ecs.event.Event;
import dev.peytob.rpg.engine.context.EcsContextManager;
import org.springframework.stereotype.Component;

@Component
public final class WindowCursorPositionContextAdapterHandler extends WindowCursorPositionEventHandler {

    private final EcsContextManager ecsContextManager;

    private final Window window;

    public WindowCursorPositionContextAdapterHandler(EcsContextManager ecsContextManager, Window window) {
        this.ecsContextManager = ecsContextManager;
        this.window = window;
    }

    @Override
    public void handle(double newX, double newY) {
        Event event = new CursorPositionEvent(
                newX,
                newY,
                newX - window.getWidth() / 2.0,
                newY - window.getHeight() / 2.0);

        ecsContextManager.addEvent(event);
    }
}
