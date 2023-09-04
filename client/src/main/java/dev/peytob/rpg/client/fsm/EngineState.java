package dev.peytob.rpg.client.fsm;

import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.context.EcsContexts;

public abstract class EngineState {

    private final EcsContext ecsContext;

    public EngineState() {
        this.ecsContext = EcsContexts.empty();
    }

    public EcsContext getEcsContext() {
        return ecsContext;
    }

    public abstract void beforeSet(EcsContext prevEcsContext);

    public abstract void onSet();

    public abstract void onUpdate();

    public abstract void beforeChange();
}
