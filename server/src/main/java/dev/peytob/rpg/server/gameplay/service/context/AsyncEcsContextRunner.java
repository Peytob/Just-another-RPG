package dev.peytob.rpg.server.gameplay.service.context;

import dev.peytob.rpg.ecs.context.EcsContext;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
class AsyncEcsContextRunner implements Runnable, EcsContextRunner {

    private final EcsContext ecsContext;

    private final String contextName;

    private Consumer<EcsContext> beforeFrameConsumer;

    private boolean isExecuting;

    public AsyncEcsContextRunner(EcsContext ecsContext, String contextName) {
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

        while (!currentThread.isInterrupted() && isExecuting) {
            executeFrame();
            Thread.yield();
        }

        log.info("Ecs context {} executing in thread {} was stopped", contextName, currentThread.getName());

        isExecuting = false;
    }

    @Override
    public EcsContext getRawContext() {
        return ecsContext;
    }

    @Override
    public String getContextName() {
        return contextName;
    }

    @Override
    public boolean isExecuting() {
        return isExecuting;
    }

    @Override
    public void executeBeforeFrame(Consumer<EcsContext> consumer) {
        beforeFrameConsumer = beforeFrameConsumer == null ? consumer : beforeFrameConsumer.andThen(consumer);
    }

    void stopRunning() {
        isExecuting = false;
    }

    private void executeFrame() {
        if (beforeFrameConsumer != null) {
            beforeFrameConsumer.accept(ecsContext);
            beforeFrameConsumer = null;
        }

        ecsContext.getSystems().forEach(system -> system.execute(ecsContext));
    }
}
