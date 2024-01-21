package dev.peytob.rpg.client.input.hid.ecs.event;

public record MouseScrollEvent(
    double xOffset,
    double yOffset
) implements HidEvent {
}
