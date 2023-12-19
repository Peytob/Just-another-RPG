package dev.peytob.rpg.client.fsm.model;

import dev.peytob.rpg.client.fsm.state.EngineState;
import dev.peytob.rpg.ecs.context.EcsContext;

public record ExecutingEngineState(
    EngineState engineState,
    EcsContext ecsContext
) {}
