package dev.peytob.rpg.client.input.hid.event;

public record MouseScrollEvent(
    double xOffset,
    double yOffset
) implements HidEvent {
}
