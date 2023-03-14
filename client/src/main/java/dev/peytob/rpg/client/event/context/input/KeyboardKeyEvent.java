package dev.peytob.rpg.client.event.context.input;

import dev.peytob.rpg.client.model.control.KeyAction;
import dev.peytob.rpg.client.model.control.KeyModifiers;
import dev.peytob.rpg.client.model.control.KeyboardKey;
import dev.peytob.rpg.ecs.event.Event;

public record KeyboardKeyEvent(
        KeyboardKey key,
        KeyAction action,
        KeyModifiers modifiers
) implements Event {
}
