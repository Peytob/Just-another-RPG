package dev.peytob.rpg.client.fsm.model;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
public class EngineStateOperationQueue {

    private final Queue<EngineStateOperation> engineStateOperationsQueue;

    public EngineStateOperationQueue() {
        this.engineStateOperationsQueue = new LinkedList<>();
    }

    public boolean isEmpty() {
        return engineStateOperationsQueue.isEmpty();
    }

    public void push(EngineStateOperation engineStateOperation) {
        engineStateOperationsQueue.add(engineStateOperation);
    }

    public EngineStateOperation peek() {
        return engineStateOperationsQueue.peek();
    }

    public EngineStateOperation pop() {
        return engineStateOperationsQueue.poll();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }
}
