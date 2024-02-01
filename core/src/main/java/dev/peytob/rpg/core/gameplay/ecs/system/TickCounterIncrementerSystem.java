package dev.peytob.rpg.core.gameplay.ecs.system;

import dev.peytob.rpg.core.gameplay.ecs.component.TickCounterComponent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

@Component
public class TickCounterIncrementerSystem implements System {

    @Override
    public void execute(EcsContext context) {
        context.getComponentsByType(TickCounterComponent.class).stream()
            .filter(TickCounterComponent::isAutoincrementEnabled)
            .forEach(TickCounterComponent::incrementCurrentTick);
    }
}
