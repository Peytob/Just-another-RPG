package dev.peytob.rpg.client.input.hid.ecs.event;

public record CursorPositionEvent(
    double x,
    double y,
    double xOffset,
    double yOffset
) implements HidEvent {
}
