package dev.peytob.rpg.core.context.component.template;

import dev.peytob.rpg.ecs.component.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsyncTaskComponent<T> implements Component {

    private final CompletableFuture<T> future;

    public AsyncTaskComponent(CompletableFuture<T> future) {
        this.future = future;
    }

    public CompletableFuture<T> getFuture() {
        return future;
    }

    public boolean isDone() {
        return future.isDone();
    }

    public T get() throws InterruptedException, ExecutionException {
        return future.get();
    }
}
