package dev.peytob.rpg.engine.state.event;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.peytob.rpg.ecs.context.EcsContext;
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
public final class StateTearDownEventBus {

    private static final Logger logger = LoggerFactory.getLogger(StateTearDownEventBus.class);

    private static final Method TEAR_DOWN_METHOD =
        Objects.requireNonNull(
            findMethod(StateTearDownEventHandler.class, "onStateTearDown", EngineState.class, EcsContext.class),
            "onStateTearDown method not found in StateTearDownEventHandler interface!");

    private final Multimap<Class<? extends EngineState>, StateTearDownEventHandler<? extends EngineState>> stateTearDownEventHandlers;

    public StateTearDownEventBus(Collection<StateTearDownEventHandler<? extends EngineState>> stateTearDownEventHandlers) {
        this.stateTearDownEventHandlers = HashMultimap.create();
        stateTearDownEventHandlers
            .forEach(handler -> this.stateTearDownEventHandlers.put(resolveEngineStateClass(handler), handler));
    }

    public void onStateTearDown(EngineState engineState, EcsContext ecsContext) {
        logger.info("Handling {} state tear down", engineState.getName());

        stateTearDownEventHandlers.entries().stream()
            .filter(entry -> entry.getKey().isInstance(engineState))
            .map(Map.Entry::getValue)
            .forEach(handler -> {
                logger.info("Handling tear down event by {} event handler", handler.getName());
                invokeMethod(TEAR_DOWN_METHOD, handler, engineState, ecsContext);
            });
    }

    @SuppressWarnings("unchecked")
    private Class<? extends EngineState> resolveEngineStateClass(StateTearDownEventHandler<? extends EngineState> handler) {
        return (Class<? extends EngineState>) resolveTypeArgument(EngineState.class, handler.getClass());
    }
}
