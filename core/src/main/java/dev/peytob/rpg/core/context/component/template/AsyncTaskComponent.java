package dev.peytob.rpg.core.context.component.template;

import dev.peytob.rpg.ecs.component.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncTaskComponent<T> implements Component {

    private final Future<T> future;

    public AsyncTaskComponent(Future<T> future) {
        this.future = future;
    }

    public Future<T> getFuture() {
        return future;
    }

    public boolean isDone() {
        return future.isDone();
    }

    public T get() throws InterruptedException, ExecutionException {
        return future.get();
    }
}
