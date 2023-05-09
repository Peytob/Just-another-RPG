package dev.peytob.rpg.client.module.control.context.event;

import dev.peytob.rpg.client.module.control.model.KeyAction;
import dev.peytob.rpg.client.module.control.model.KeyModifiers;
import dev.peytob.rpg.client.module.control.model.MouseButton;
import dev.peytob.rpg.ecs.event.Event;

public record MouseButtonEvent(
        MouseButton key,
        KeyAction action,
        KeyModifiers modifiers
) implements Event {
}
