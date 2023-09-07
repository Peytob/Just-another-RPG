package dev.peytob.rpg.client.fsm.service;

import dev.peytob.rpg.client.fsm.EngineState;

public interface EngineStateManager {

    void updateState();

    void executeFrameSystems();

    void changeState(EngineState engineState);
}
