package dev.peytob.rpg.core.gameplay.ecs.component;

import dev.peytob.rpg.ecs.component.Component;

import java.util.concurrent.CompletableFuture;

public class AsyncTaskComponent<T> implements Component {

    private final CompletableFuture<T> future;

    private final String taskName;

    public AsyncTaskComponent(CompletableFuture<T> future, String taskName) {
        this.future = future;
        this.taskName = taskName;
    }

    public CompletableFuture<T> getFuture() {
        return future;
    }

    public String getTaskName() {
        return taskName;
    }
}
