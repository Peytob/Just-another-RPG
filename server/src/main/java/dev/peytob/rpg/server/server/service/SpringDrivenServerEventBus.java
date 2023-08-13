package dev.peytob.rpg.server.server.service;

import dev.peytob.rpg.server.server.event.ServerEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class SpringDrivenServerEventBus implements ServerEventBus {

    private final ApplicationEventPublisher applicationEventPublisher;

    public SpringDrivenServerEventBus(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publishServerEvent(ServerEvent serverEvent) {
        applicationEventPublisher.publishEvent(serverEvent);
    }
}
