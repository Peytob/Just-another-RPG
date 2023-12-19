package dev.peytob.rpg.client.input.hid.event;

import dev.peytob.rpg.client.input.hid.model.KeyAction;
import dev.peytob.rpg.client.input.hid.model.KeyModifiers;
import dev.peytob.rpg.client.input.hid.model.MouseButton;

public record MouseButtonEvent(
    MouseButton key,
    KeyAction action,
    KeyModifiers modifiers
) implements HidEvent {
}
