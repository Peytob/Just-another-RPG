package dev.peytob.rpg.client.fsm.service;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import dev.peytob.rpg.client.fsm.state.EngineState;
import dev.peytob.rpg.ecs.system.OrderedSystem;

import java.util.Collection;

public class StateAnnotatedSystemsManager implements StateSystemsManager {

    private final Multimap<Class<? extends EngineState>, OrderedSystem> systems;

    public StateAnnotatedSystemsManager(Multimap<Class<? extends EngineState>, OrderedSystem> systems) {
        this.systems = Multimaps.unmodifiableMultimap(systems);
    }

    @Override
    public Collection<OrderedSystem> getSystemsForState(EngineState engineState) {
        return getSystemsForState(engineState.getClass());
    }

    @Override
    public Collection<OrderedSystem> getSystemsForState(Class<? extends EngineState> engineStateClass) {
        // TODO If state extends other state, then parent systems should be returned too
        return systems.get(engineStateClass);
    }
}