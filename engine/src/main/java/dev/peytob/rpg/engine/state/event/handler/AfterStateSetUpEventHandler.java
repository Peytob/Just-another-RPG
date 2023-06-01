package dev.peytob.rpg.engine.state.event.handler;

import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.engine.event.EventHandler;
import dev.peytob.rpg.engine.state.EngineState;
import dev.peytob.rpg.engine.state.event.instance.AfterStateSetUpEvent;
import dev.peytob.rpg.engine.utils.reflection.GenericReflectionUtils;

public abstract class AfterStateSetUpEventHandler<T extends EngineState> implements EventHandler<AfterStateSetUpEvent> {

    abstract public void handleStateTearDown(T engineState, EcsContext newContext);

    @Override
    @SuppressWarnings("unchecked")
    public final void handleEvent(AfterStateSetUpEvent event) {
        Class<?> engineStateClass = GenericReflectionUtils.resolveTypeArgument(getClass(), AfterStateSetUpEventHandler.class);

        if (engineStateClass.equals(event.engineState().getClass())) {
            handleStateTearDown((T) event.engineState(), event.context());
        }
    }
}
