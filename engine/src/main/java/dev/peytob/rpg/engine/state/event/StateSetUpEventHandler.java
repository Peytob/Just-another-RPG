package dev.peytob.rpg.engine.state.event;

import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.engine.event.EventHandler;
import dev.peytob.rpg.engine.state.EngineState;
import dev.peytob.rpg.engine.state.event.instance.StateSetUpEvent;
import dev.peytob.rpg.engine.utils.reflection.GenericReflectionUtils;

public abstract class StateSetUpEventHandler<T extends EngineState> implements EventHandler<StateSetUpEvent> {

    abstract public void onStateSetUp(T engineState, EcsContextBuilder contextBuilder);

    @Override
    @SuppressWarnings("unchecked")
    public void handleEvent(StateSetUpEvent event) {
        Class<?> engineStateClass = GenericReflectionUtils.resolveTypeArgument(getClass(), StateSetUpEventHandler.class);

        if (engineStateClass.isInstance(event.engineState())) {
            onStateSetUp((T) event.engineState(), event.contextBuilder());
        }
    }
}
