package dev.peytob.rpg.client.gameplay.ecs.event;

import dev.peytob.rpg.core.network.model.server.WorldState;
import dev.peytob.rpg.ecs.event.Event;

import java.time.Instant;

public class WorldUpdatedEvent implements Event {

    private final WorldState worldState;

    private final Instant handledAt;

    public WorldUpdatedEvent(WorldState worldState, Instant handledAt) {
        this.worldState = worldState;
        this.handledAt = handledAt;
    }

    public WorldState getWorldState() {
        return worldState;
    }

    public Instant getHandledAt() {
        return handledAt;
    }
}
