package dev.peytob.rpg.engine.state;

public interface EngineState {

    default String getName() {
        return getClass().getSimpleName();
    }
}
