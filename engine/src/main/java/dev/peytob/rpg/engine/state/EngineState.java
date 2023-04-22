package dev.peytob.rpg.engine.state;

import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.OrderedSystem;

import java.util.Collection;

public interface EngineState {

    String getName();

    Collection<OrderedSystem> getSystems();

    void onSetUp(EcsContext ecsContext);

    void onTearDown(EcsContext ecsContext);
}
