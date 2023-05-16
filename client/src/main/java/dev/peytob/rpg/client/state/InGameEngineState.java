package dev.peytob.rpg.client.state;

import dev.peytob.rpg.engine.state.EngineState;
import org.springframework.stereotype.Component;

@Component
public final class InGameEngineState implements EngineState {

    @Override
    public String getName() {
        return "InGame";
    }
}
