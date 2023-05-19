package dev.peytob.rpg.engine.state.event;

import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.engine.state.EngineState;
import org.springframework.core.Ordered;

public interface StateSetUpEventHandler<T extends EngineState> extends Ordered {

    void onStateSetUp(EcsContextBuilder contextBuilder, T engineState);

    @Override
    default int getOrder() {
        return 0;
    }

    default String getName() {
        return getClass().getSimpleName();
    }
}
