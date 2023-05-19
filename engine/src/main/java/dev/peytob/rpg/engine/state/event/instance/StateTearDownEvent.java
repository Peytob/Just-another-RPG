package dev.peytob.rpg.engine.state.event.instance;

import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.engine.state.EngineState;

public record StateTearDownEvent(
    EngineState engineState,
    EcsContext context
) {
}
