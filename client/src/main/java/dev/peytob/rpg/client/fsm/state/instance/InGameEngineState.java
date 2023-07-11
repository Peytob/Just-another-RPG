package dev.peytob.rpg.client.fsm.state.instance;

import dev.peytob.rpg.client.fsm.EngineState;
import org.springframework.stereotype.Component;

@Component
public final class InGameEngineState implements EngineState {

    @Override
    public String getName() {
        return "InGame";
    }
}
