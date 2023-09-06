package dev.peytob.rpg.client.fsm;

public interface EngineState {

    default String getName() {
        return getClass().getSimpleName();
    }
}
