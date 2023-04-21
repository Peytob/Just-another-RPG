package dev.peytob.rpg.client.event.handler.window.key;

import dev.peytob.rpg.client.context.event.input.KeyboardKeyEvent;
import dev.peytob.rpg.client.model.control.KeyAction;
import dev.peytob.rpg.client.model.control.KeyModifiers;
import dev.peytob.rpg.client.model.control.KeyboardKey;
import dev.peytob.rpg.ecs.event.Event;
import dev.peytob.rpg.engine.context.EcsContextManager;
import org.springframework.stereotype.Component;

@Component
public final class WindowKeyContextAdapterHandler extends WindowKeyEventHandler {

    private final EcsContextManager ecsContextManager;

    public WindowKeyContextAdapterHandler(EcsContextManager ecsContextManager) {
        this.ecsContextManager = ecsContextManager;
    }

    @Override
    public void handle(KeyboardKey key, KeyAction action, KeyModifiers modifiers) {
        Event event = new KeyboardKeyEvent(key, action, modifiers);
        ecsContextManager.addEvent(event);
    }
}
