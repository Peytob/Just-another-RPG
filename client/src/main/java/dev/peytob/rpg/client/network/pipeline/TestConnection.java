package dev.peytob.rpg.client.network.pipeline;

import dev.peytob.rpg.client.network.model.NetworkScheme;
import dev.peytob.rpg.client.network.model.ServerDetails;
import dev.peytob.rpg.client.network.service.NetworkManager;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;

//@Component
@Slf4j
@RequiredArgsConstructor
public class TestConnection implements InitializingPipelineStep {

    private final NetworkManager networkManager;

    @Override
    public void execute() {
        ServerDetails serverDetails = new ServerDetails("localhost", 8082, NetworkScheme.HTTP);
        networkManager.loginToServer("string", "pAssW0rd!", serverDetails);

        WebSocketClient websocketClient = networkManager.getWebsocketClient();
        websocketClient.execute(new WebSocketHandler() {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            }

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
            }

            @Override
            public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
            }

            @Override
            public boolean supportsPartialMessages() {
                return false;
            }
        }, "/context/events");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
