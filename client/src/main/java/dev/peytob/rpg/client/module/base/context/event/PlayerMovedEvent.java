package dev.peytob.rpg.client.module.base.context.event;

import dev.peytob.rpg.ecs.event.Event;
import dev.peytob.rpg.math.vector.Vec2;

public record PlayerMovedEvent(
    Vec2 normalizedDirection
) implements Event {
}
