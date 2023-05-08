package dev.peytob.rpg.client.module.graphic.event.handler.window.mouse.button;

import dev.peytob.rpg.client.context.event.input.MouseButtonEvent;
import dev.peytob.rpg.client.model.control.KeyAction;
import dev.peytob.rpg.client.model.control.KeyModifiers;
import dev.peytob.rpg.client.model.control.MouseButton;
import dev.peytob.rpg.ecs.event.Event;
import dev.peytob.rpg.engine.context.EcsContextManager;
import org.springframework.stereotype.Component;

@Component
public final class WindowMouseButtonContextAdapterHandler extends WindowMouseButtonEventHandler {

    private final EcsContextManager ecsContextManager;

    public WindowMouseButtonContextAdapterHandler(EcsContextManager ecsContextManager) {
        this.ecsContextManager = ecsContextManager;
    }

    @Override
    public void handle(MouseButton button, KeyAction action, KeyModifiers modifiers) {
        Event event = new MouseButtonEvent(button, action, modifiers);
        ecsContextManager.addEvent(event);
    }
}
