package dev.peytob.rpg.client.fsm.event.instance;

import dev.peytob.rpg.client.fsm.model.ExecutingEngineState;

public record BeforeEngineStatePopEvent(
    ExecutingEngineState executingEngineState
) {
}
