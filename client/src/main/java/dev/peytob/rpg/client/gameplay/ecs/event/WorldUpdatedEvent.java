package dev.peytob.rpg.client.gameplay.ecs.event;

import dev.peytob.rpg.core.network.model.server.WorldState;
import dev.peytob.rpg.ecs.event.Event;

public class WorldUpdatedEvent implements Event {

    private final WorldState worldState;

    public WorldUpdatedEvent(WorldState worldState) {
        this.worldState = worldState;
    }

    public WorldState getWorldState() {
        return worldState;
    }
}
