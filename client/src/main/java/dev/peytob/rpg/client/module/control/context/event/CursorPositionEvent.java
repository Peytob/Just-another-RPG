package dev.peytob.rpg.client.module.control.context.event;

import dev.peytob.rpg.ecs.event.Event;

public record CursorPositionEvent(
        double x,
        double y,
        double xOffset,
        double yOffset
) implements Event {
}
