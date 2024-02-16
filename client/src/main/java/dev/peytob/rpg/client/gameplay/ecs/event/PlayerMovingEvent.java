package dev.peytob.rpg.client.gameplay.ecs.event;

import dev.peytob.rpg.ecs.event.Event;
import dev.peytob.rpg.math.vector.Vec2;

public record PlayerMovingEvent(
    Vec2 movingDirection,
    float movingSpeed
) implements Event {
}
