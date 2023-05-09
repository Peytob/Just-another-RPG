package dev.peytob.rpg.client.module.control.context.event;

import dev.peytob.rpg.ecs.event.Event;

public record MouseScrollEvent(
        double xOffset,
        double yOffset
) implements Event {
}
