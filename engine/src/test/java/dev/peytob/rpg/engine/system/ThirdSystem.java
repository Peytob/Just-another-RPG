package dev.peytob.rpg.engine.system;

import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

@Component
public class ThirdSystem implements System {

    @Override
    public void execute(EcsContext context) {
        // Nothing...
    }
}