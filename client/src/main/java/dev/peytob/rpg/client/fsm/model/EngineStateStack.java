package dev.peytob.rpg.client.fsm.model;

import org.springframework.stereotype.Component;

import java.util.Stack;

@Component
public class EngineStateStack {

    private final Stack<ExecutingEngineState> engineStatesStack;

    public EngineStateStack() {
        this.engineStatesStack = new Stack<>();
    }

    public void push(ExecutingEngineState engineState) {
        engineStatesStack.push(engineState);
    }

    public ExecutingEngineState pop() {
        return engineStatesStack.pop();
    }

    public ExecutingEngineState peek() {
        return engineStatesStack.peek();
    }

    public boolean isEmpty() {
        return engineStatesStack.isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }
}
