package dev.peytob.rpg.client.event.context.input;

import dev.peytob.rpg.ecs.event.Event;

public record MouseScrollEvent(
        double xOffset,
        double yOffset
) implements Event {
}
