package dev.peytob.rpg.client.context.event.input;

import dev.peytob.rpg.client.model.control.KeyAction;
import dev.peytob.rpg.client.model.control.KeyModifiers;
import dev.peytob.rpg.client.model.control.MouseButton;
import dev.peytob.rpg.ecs.event.Event;

public record MouseButtonEvent(
        MouseButton key,
        KeyAction action,
        KeyModifiers modifiers
) implements Event {
}
