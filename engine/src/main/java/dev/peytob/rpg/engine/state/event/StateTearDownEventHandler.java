package dev.peytob.rpg.engine.state.event;

import dev.peytob.rpg.engine.state.EngineState;
import org.springframework.core.Ordered;

public interface StateTearDownEventHandler<T extends EngineState> extends Ordered {

    void onStateTearDown(T engineState);

    @Override
    default int getOrder() {
        return 0;
    }
}
