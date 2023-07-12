package dev.peytob.rpg.client.module.base.context.event;

import dev.peytob.rpg.ecs.event.Event;
import dev.peytob.rpg.client.fsm.EngineState;

public class ChangeStateEcsEvent implements Event {

    private final EngineState engineState;

    public ChangeStateEcsEvent(EngineState engineState) {
        this.engineState = engineState;
    }

    public EngineState getEngineState() {
        return engineState;
    }
}
