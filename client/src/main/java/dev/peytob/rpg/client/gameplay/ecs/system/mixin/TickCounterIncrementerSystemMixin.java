package dev.peytob.rpg.client.gameplay.ecs.system.mixin;

import dev.peytob.rpg.client.fsm.annotation.IncludeInAllStates;
import dev.peytob.rpg.client.fsm.annotation.SystemMixin;
import dev.peytob.rpg.core.gameplay.ecs.system.TickCounterIncrementerSystem;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.AFTER_ALL;

@Component
@IncludeInAllStates(order = AFTER_ALL)
public class TickCounterIncrementerSystemMixin implements SystemMixin {

    @Override
    public Class<? extends System> getTargetSystem() {
        return TickCounterIncrementerSystem.class;
    }
}
