package dev.peytob.rpg.engine.state.event;

import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.engine.state.EngineState;
import org.springframework.core.Ordered;

public interface StateTearDownEventHandler<T extends EngineState> extends Ordered {

    void onStateTearDown(T engineState, EcsContext ecsContext);

    @Override
    default int getOrder() {
        return 0;
    }

    default String getName() {
        return getClass().getSimpleName();
    }
}
