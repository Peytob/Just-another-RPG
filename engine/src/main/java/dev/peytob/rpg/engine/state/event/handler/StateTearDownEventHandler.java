package dev.peytob.rpg.engine.state.event.handler;

import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.engine.event.EventHandler;
import dev.peytob.rpg.engine.state.EngineState;
import dev.peytob.rpg.engine.state.event.instance.StateTearDownEvent;
import dev.peytob.rpg.engine.utils.reflection.GenericReflectionUtils;

public abstract class StateTearDownEventHandler<T extends EngineState> implements EventHandler<StateTearDownEvent> {

    abstract public void handleStateTearDown(T engineState, EcsContext oldContext);

    @Override
    @SuppressWarnings("unchecked")
    public final void handleEvent(StateTearDownEvent event) {
        Class<?> engineStateClass = GenericReflectionUtils.resolveTypeArgument(getClass(), StateTearDownEventHandler.class);

        if (engineStateClass.equals(event.engineState().getClass())) {
            handleStateTearDown((T) event.engineState(), event.context());
        }
    }
}
