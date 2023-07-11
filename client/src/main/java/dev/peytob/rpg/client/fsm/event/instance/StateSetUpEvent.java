package dev.peytob.rpg.client.fsm.event.instance;

import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.client.fsm.EngineState;

public record StateSetUpEvent(
    EngineState engineState,
    EcsContextBuilder contextBuilder
) {
}
