package dev.peytob.rpg.client.fsm.state;

public interface EngineState {

    default String getName() {
        return getClass().getSimpleName();
    }
}
