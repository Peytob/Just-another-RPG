package dev.peytob.rpg.engine.state.event.instance;

import dev.peytob.rpg.engine.state.EngineState;

public record ChangeStateEvent(
    EngineState nextEngineState
) {
}
