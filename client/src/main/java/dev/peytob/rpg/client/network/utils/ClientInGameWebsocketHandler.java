package dev.peytob.rpg.client.network.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.peytob.rpg.client.network.ecs.component.WebsocketSessionComponent;
import dev.peytob.rpg.client.network.model.ServerDetails;
import dev.peytob.rpg.core.network.model.server.WorldState;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.entity.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;

import java.util.Optional;

public class ClientInGameWebsocketHandler implements WebSocketHandler {

    private final Logger log;
    private final ObjectMapper objectMapper;
    private final EcsContext ecsContext;

    public ClientInGameWebsocketHandler(ServerDetails serverDetails, EcsContext ecsContext) {
        this.ecsContext = ecsContext;
        this.log = LoggerFactory.getLogger("ClientInGameWebsocketHandler: " + serverDetails.host());
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws JsonProcessingException {
        log.debug("Handling websocket message {}", message);

        if (message instanceof TextMessage textMessage) {
            WorldState worldState = objectMapper.readValue(textMessage.getPayload(), WorldState.class);
            log.info("Received world state data from server...");
        } else {
            log.error("Unknown type of message, cannot be deserialized");
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        // TODO Create ECS exception handler
        log.error("Transport error while executing websocket action", exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        log.info("Websocket connection closed with status {}", closeStatus);

        getSessionComponentEntity(session)
            .ifPresent(entity -> entity.removeComponent(WebsocketSessionComponent.class));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private Optional<Entity> getSessionComponentEntity(WebSocketSession session) {
        return ecsContext.getComponentsByType(WebsocketSessionComponent.class).stream()
            .filter(component -> session.equals(component.getWebSocketSession()))
            .findFirst()
            .map(ecsContext::getComponentEntity);
    }
}
