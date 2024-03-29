package dev.peytob.rpg.client.fsm.event.instance;

import dev.peytob.rpg.client.fsm.state.EngineState;
import dev.peytob.rpg.ecs.context.EcsContextBuilder;

public record BeforeEngineStatePushEvent(
    EcsContextBuilder contextBuilder,
    EngineState engineState
) {
}
