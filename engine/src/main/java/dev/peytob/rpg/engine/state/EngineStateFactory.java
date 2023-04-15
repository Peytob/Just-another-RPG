package dev.peytob.rpg.engine.state;

import dev.peytob.rpg.engine.exception.state.EngineStateNotFound;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public final class EngineStateFactory {

    private final Map<String, EngineState> states;

    public EngineStateFactory(Collection<EngineState> engineStates) {
        this.states = engineStates.stream()
                .collect(Collectors.toUnmodifiableMap(EngineState::name, Function.identity()));;
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
