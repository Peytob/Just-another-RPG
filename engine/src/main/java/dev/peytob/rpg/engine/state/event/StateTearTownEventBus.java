package dev.peytob.rpg.engine.state.event;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.peytob.rpg.engine.state.EngineState;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Objects;

import static dev.peytob.rpg.engine.utils.reflection.GenericReflectionUtils.*;

@Component
public final class StateTearTownEventBus {

    private static final Method TEAR_DOWN_METHOD =
        Objects.requireNonNull(
            findMethod(StateTearDownEventHandler.class, "onStateTearDown", EngineState.class),
            "onStateTearDown method not found in StateTearDownEventHandler interface!");

    private final Multimap<Class<? extends EngineState>, StateTearDownEventHandler<? extends EngineState>> stateTearDownEventHandlers;

    public StateTearTownEventBus(Collection<StateTearDownEventHandler<? extends EngineState>> stateTearDownEventHandlers) {
        this.stateTearDownEventHandlers = HashMultimap.create();
        stateTearDownEventHandlers
            .forEach(handler -> this.stateTearDownEventHandlers.put(resolveEngineStateClass(handler), handler));
    }

    public void onStateTearDown(EngineState engineState) {
        stateTearDownEventHandlers
            .get(engineState.getClass())
            .forEach(handler -> invokeMethod(TEAR_DOWN_METHOD, handler, engineState));
    }

    @SuppressWarnings("unchecked")
    private Class<? extends EngineState> resolveEngineStateClass(StateTearDownEventHandler<? extends EngineState> handler) {
        return (Class<? extends EngineState>) resolveTypeArgument(EngineState.class, handler.getClass());
    }
}
