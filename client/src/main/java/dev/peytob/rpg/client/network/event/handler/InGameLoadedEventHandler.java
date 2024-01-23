package dev.peytob.rpg.client.network.event.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.peytob.rpg.client.fsm.event.instance.AfterEngineStatePushEvent;
import dev.peytob.rpg.client.fsm.state.EngineState;
import dev.peytob.rpg.client.fsm.state.InGameState;
import dev.peytob.rpg.client.network.ecs.component.WebsocketSessionComponent;
import dev.peytob.rpg.client.network.model.ServerDetails;
import dev.peytob.rpg.client.network.service.NetworkManager;
import dev.peytob.rpg.core.gameplay.ecs.component.AsyncTaskComponent;
import dev.peytob.rpg.core.network.model.server.WorldState;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.engine.pipeline.InitializingPipeline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.WebSocketClient;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class InGameLoadedEventHandler {

    private final InGameState inGameState;

    private final NetworkManager networkManager;

    @EventListener
    void handleEvent(AfterEngineStatePushEvent event) {
        EngineState engineState = event.executingEngineState().engineState();

        if (!inGameState.equals(engineState)) {
            return;
        }

        EcsContext ecsContext = event.executingEngineState().ecsContext();

        WebSocketClient websocketClient = networkManager.getWebsocketClient();
        ServerDetails serverDetails = networkManager.getServerDetails();

        websocketClient.execute(new WebSocketHandler() {

            private final Logger log = LoggerFactory.getLogger(serverDetails.host());

            private final ObjectMapper objectMapper = new ObjectMapper();

            private String connectionEntityId;

            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                log.info("Connection to server {} was established; making ECS entities", serverDetails);

                Entity entity = ecsContext.createEntity();
                connectionEntityId = entity.getId();

                WebsocketSessionComponent websocketSessionComponent = new WebsocketSessionComponent(session);
                entity.bindComponent(websocketSessionComponent);
            }

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
                log.debug("Handling websocket message {}", message);

                if (message instanceof TextMessage textMessage) {
                    WorldState worldState = objectMapper.readValue(textMessage.getPayload(), WorldState.class);
                    log.info("Received world state data from server...");
                } else {
                    log.error("Unknown type of message, cannot be deserialized");
                }
            }

            @Override
            public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
                log.error("Transport error while executing websocket action", exception);
            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                log.info("Websocket connection closed with status {}", closeStatus);
                ecsContext.getEntityById(connectionEntityId).ifPresent(entity ->
                    entity.removeComponent(WebsocketSessionComponent.class));
            }

            @Override
            public boolean supportsPartialMessages() {
                return false;
            }
        }, "/context/events");
    }
}
