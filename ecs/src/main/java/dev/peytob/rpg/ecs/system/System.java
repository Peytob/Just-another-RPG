package dev.peytob.rpg.ecs.system;

import dev.peytob.rpg.ecs.context.EcsContext;

public interface System {

    void execute(EcsContext context);
}
