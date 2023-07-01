package dev.peytob.rpg.engine.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * An adapter between application and spring event bus. Should isolate implementation of event bus from other app.
 */
@Component
public class SpringAdapterEngineEventBus implements EngineEventBus {

    private final ApplicationEventPublisher applicationEventPublisher;

    public SpringAdapterEngineEventBus(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publishEvent(Object event) {
        applicationEventPublisher.publishEvent(event);
    }
}
