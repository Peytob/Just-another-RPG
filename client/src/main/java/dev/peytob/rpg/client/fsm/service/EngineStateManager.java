package dev.peytob.rpg.client.fsm.service;

import dev.peytob.rpg.client.fsm.state.EngineState;
import dev.peytob.rpg.client.fsm.model.ExecutingEngineState;

public interface EngineStateManager {

    void pushEngineState(EngineState engineState);

    void popEngineState();

    void chargeEngineState(EngineState engineState);

    void flushEngineStates();

    boolean isStatePresent();

    ExecutingEngineState getCurrentEngineState();
}
