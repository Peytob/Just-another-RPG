package dev.peytob.rpg.client.network.event.handler;

import dev.peytob.rpg.client.fsm.event.instance.AfterEngineStatePushEvent;
import dev.peytob.rpg.client.fsm.state.EngineState;
import dev.peytob.rpg.client.fsm.state.InGameState;
import dev.peytob.rpg.client.network.ecs.component.WebsocketSessionComponent;
import dev.peytob.rpg.client.network.model.ServerDetails;
import dev.peytob.rpg.client.network.service.NetworkManager;
import dev.peytob.rpg.client.network.utils.ClientInGameWebsocketHandler;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.entity.Entity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;

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

        websocketClient
            .execute(new ClientInGameWebsocketHandler(serverDetails, ecsContext), "/context/events")
            .thenApply(webSocketSession -> {
                log.info("Connection to server {} was established; making ECS entities", serverDetails);

                Entity entity = ecsContext.createEntity();

                WebsocketSessionComponent websocketSessionComponent = new WebsocketSessionComponent(webSocketSession);
                entity.bindComponent(websocketSessionComponent);

                return webSocketSession;
            })
            .exceptionally(throwable -> {
                // TODO Create exception handler
                log.error("Connection to server was failed... Rip", throwable);
                return null;
            });
    }
}
