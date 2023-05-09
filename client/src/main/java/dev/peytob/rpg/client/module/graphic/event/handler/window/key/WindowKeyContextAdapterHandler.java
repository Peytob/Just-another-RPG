package dev.peytob.rpg.client.module.graphic.event.handler.window.key;

import dev.peytob.rpg.client.module.control.context.event.KeyboardKeyEvent;
import dev.peytob.rpg.client.module.control.model.KeyAction;
import dev.peytob.rpg.client.module.control.model.KeyModifiers;
import dev.peytob.rpg.client.module.control.model.KeyboardKey;
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
