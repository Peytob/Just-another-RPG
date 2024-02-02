package dev.peytob.rpg.server.gameplay.service.context;

import dev.peytob.rpg.ecs.context.EcsContext;

import java.util.function.Consumer;

public interface WorldContextRunner {

    EcsContext getRawContext();

    String getContextName();

    boolean isExecuting();

    void executeBeforeFrame(Consumer<EcsContext> consumer);
}
