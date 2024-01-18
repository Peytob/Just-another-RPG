package dev.peytob.rpg.client.network.pipeline;

import dev.peytob.rpg.client.network.model.NetworkScheme;
import dev.peytob.rpg.client.network.model.ServerDetails;
import dev.peytob.rpg.client.network.service.NetworkManager;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.WebSocketClient;

import java.net.URI;

import static dev.peytob.rpg.client.network.utils.DefaultHeaders.AUTHORIZATION_HEADER;

@Component
@Slf4j
@RequiredArgsConstructor
public class TestConnection implements InitializingPipelineStep {

    private final NetworkManager networkManager;

    @Override
    public void execute() {
        ServerDetails serverDetails = new ServerDetails("localhost", 8082, NetworkScheme.HTTP);
        networkManager.loginToServer("string", "pAssW0rd!", serverDetails);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(AUTHORIZATION_HEADER, networkManager.getConnectionToken());

        WebSocketClient websocketClient = networkManager.getWebsocketClient();
        websocketClient.execute(new WebSocketHandler() {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                System.out.println("afterConnectionEstablished");
            }

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
                System.out.println(message);
            }

            @Override
            public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
                System.out.println(exception);
            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                System.out.println("afterConnectionClosed");
            }

            @Override
            public boolean supportsPartialMessages() {
                return false;
            }
        }, new WebSocketHttpHeaders(httpHeaders), URI.create("ws://localhost:8082/context/events"));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
