package dev.peytob.rpg.client.fsm.event.instance;

import dev.peytob.rpg.client.fsm.EngineState;

public record ChangeStateEvent(
    EngineState nextEngineState
) {
}
