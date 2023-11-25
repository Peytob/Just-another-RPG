package dev.peytob.rpg.server.gameplay.context;

import dev.peytob.rpg.ecs.context.EcsContext;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class EcsContextRunner implements Runnable {

    private final EcsContext ecsContext;

    private final String contextName;

    private Consumer<EcsContext> beforeFrameConsumer;

    private boolean isExecuting;

    public EcsContextRunner(EcsContext ecsContext, String contextName) {
        this.ecsContext = ecsContext;
        this.beforeFrameConsumer = null;
        this.isExecuting = false;
        this.contextName = contextName;
    }

    @Override
    public void run() {
        final Thread currentThread = Thread.currentThread();
        log.info("Starting ecs context {} executing in thread {}", contextName, currentThread.getName());

        isExecuting = true;

        while (!currentThread.isInterrupted()) {
            executeBeforeFrameConsumer();
            Thread.yield();
        }

        log.info("Ecs context {} executing in thread {} was stopped", contextName, currentThread.getName());

        isExecuting = false;
    }

    public EcsContext getRawContext() {
        return ecsContext;
    }

    public boolean isExecuting() {
        return isExecuting;
    }

    private void executeBeforeFrameConsumer() {
        if (beforeFrameConsumer != null) {
            beforeFrameConsumer.accept(ecsContext);
        }

        ecsContext.getSystems().forEach(system -> system.execute(ecsContext));
    }

    public void executeBeforeFrame(Consumer<EcsContext> consumer) {
        beforeFrameConsumer = beforeFrameConsumer == null ? consumer : beforeFrameConsumer.andThen(consumer);
    }
}
