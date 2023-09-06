package dev.peytob.rpg.client.fsm.service;

import dev.peytob.rpg.client.fsm.EngineState;
import dev.peytob.rpg.ecs.system.OrderedSystem;

import java.util.Collection;

public interface StateSystemsManager {

    Collection<OrderedSystem> getSystemsForState(EngineState engineState);

    Collection<OrderedSystem> getSystemsForState(Class<? extends EngineState> engineStateClass);
}
