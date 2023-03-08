package dev.peytob.rpg.engine.state;

import dev.peytob.rpg.engine.exception.state.EngineStateNotFound;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public final class EngineStateFactory {

    private final Map<String, EngineState> states;

    public EngineStateFactory(Map<String, EngineState> states) {
        this.states = states;
    }

    public EngineState getState(String name) {
        EngineState state = states.get(name);

        if (null == state) {
            throw new EngineStateNotFound("Engine state with name " + name + " not found in context");
        }

        return state;
    }

    public Integer getSystemsCount() {
        return states.size();
    }
}
