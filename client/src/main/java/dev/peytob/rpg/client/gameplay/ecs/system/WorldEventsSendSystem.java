package dev.peytob.rpg.client.gameplay.ecs.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.peytob.rpg.client.fsm.annotation.IncludeInState;
import dev.peytob.rpg.client.fsm.state.InGameState;
import dev.peytob.rpg.client.network.ecs.component.WebsocketSessionComponent;
import dev.peytob.rpg.core.network.model.client.ClientEvent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collection;

import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.WORLD_STATE_UPDATING;

@Component
@IncludeInState(value = InGameState.class, order = WORLD_STATE_UPDATING - 1)
@Slf4j
@RequiredArgsConstructor
public class WorldEventsSendSystem implements System {

    private final ObjectMapper objectMapper;

    @Override
    public void execute(EcsContext context) {
        Collection<ClientEvent> clientEvents = context.getEventsByType(ClientEvent.class);

        context.getSingletonComponentByType(WebsocketSessionComponent.class)
            .map(WebsocketSessionComponent::getWebSocketSession)
            .ifPresent(websocketSession -> sendEvents(websocketSession, clientEvents));

        clientEvents.forEach(context::removeEvent);
    }

    private void sendEvents(WebSocketSession websocketSession, Collection<ClientEvent> clientEvents) {
        try {
            String jsonData = objectMapper.writeValueAsString(clientEvents);
            TextMessage textMessage = new TextMessage(jsonData);
            websocketSession.sendMessage(textMessage);
        } catch (IOException e) {
            // TODO Handle exception
            throw new RuntimeException(e);
        }
    }
}
