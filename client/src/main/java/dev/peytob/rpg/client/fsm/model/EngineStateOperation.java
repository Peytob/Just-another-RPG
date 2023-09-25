package dev.peytob.rpg.client.fsm.model;

import dev.peytob.rpg.client.fsm.EngineState;

public sealed interface EngineStateOperation permits EngineStateOperation.PopEngineStateOperation, EngineStateOperation.PushEngineStateOperation {

    static EngineStateOperation push(EngineState engineState) {
        return new PushEngineStateOperation(engineState);
    }

    static EngineStateOperation pop() {
        return new PopEngineStateOperation();
    }

    record PopEngineStateOperation() implements EngineStateOperation {}

    record PushEngineStateOperation(EngineState engineState) implements EngineStateOperation {}
}
