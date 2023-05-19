package dev.peytob.rpg.engine.state.event;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.engine.state.EngineState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import static dev.peytob.rpg.engine.utils.reflection.GenericReflectionUtils.*;

@Component
public final class StateSetUpEventBus {

    private static final Logger logger = LoggerFactory.getLogger(StateSetUpEventBus.class);

    private static final Method STATE_UP_METHOD =
        Objects.requireNonNull(
            findMethod(StateSetUpEventHandler.class, "onStateSetUp", EcsContextBuilder.class, EngineState.class),
            "onStateSetUp method not found in StateSetUpEventHandler interface!");

    private final Multimap<Class<? extends EngineState>, StateSetUpEventHandler<? extends EngineState>> stateSetUpEventHandlers;

    public StateSetUpEventBus(Collection<StateSetUpEventHandler<? extends EngineState>> stateSetUpEventHandlers) {
        this.stateSetUpEventHandlers = HashMultimap.create();
        stateSetUpEventHandlers
            .forEach(handler -> this.stateSetUpEventHandlers.put(resolveEngineStateClass(handler), handler));
    }

    public void onStateSetUp(EcsContextBuilder contextBuilder, EngineState engineState) {
        logger.info("Handling {} state set up", engineState.getName());

        stateSetUpEventHandlers.entries().stream()
            .filter(entry -> entry.getKey().isInstance(engineState))
            .map(Map.Entry::getValue)
            .forEach(handler -> {
                logger.info("Handling set up event by {} event handler", handler.getName());
                invokeMethod(STATE_UP_METHOD, handler, contextBuilder, engineState);
            });
    }

    @SuppressWarnings("unchecked")
    private Class<? extends EngineState> resolveEngineStateClass(StateSetUpEventHandler<? extends EngineState> handler) {
        return (Class<? extends EngineState>) resolveTypeArgument(handler.getClass(), StateSetUpEventHandler.class);
    }
}
