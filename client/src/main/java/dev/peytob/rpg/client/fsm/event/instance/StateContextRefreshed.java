package dev.peytob.rpg.client.fsm.event.instance;

import dev.peytob.rpg.client.fsm.EngineState;
import dev.peytob.rpg.ecs.context.EcsContext;

public record StateContextRefreshed(
    EngineState engineState,
    EcsContext refreshedEcsContext
) {
}
