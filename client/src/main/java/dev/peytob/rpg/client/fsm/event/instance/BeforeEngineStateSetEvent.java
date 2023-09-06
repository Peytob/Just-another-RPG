package dev.peytob.rpg.client.fsm.event.instance;

import dev.peytob.rpg.client.fsm.EngineState;
import dev.peytob.rpg.ecs.context.EcsContextBuilder;

public record BeforeEngineStateSetEvent(
    EcsContextBuilder contextBuilder,
    EngineState engineState
) {
}
