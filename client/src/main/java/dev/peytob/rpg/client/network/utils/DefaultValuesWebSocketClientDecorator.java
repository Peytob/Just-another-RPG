package dev.peytob.rpg.client.network.utils;

import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

public class DefaultValuesWebSocketClientDecorator implements WebSocketClient {

    private final WebSocketClient webSocketClient;

    public DefaultValuesWebSocketClientDecorator(WebSocketClient webSocketClient) {
        this.webSocketClient = webSocketClient;
    }

    @Override
    public CompletableFuture<WebSocketSession> execute(WebSocketHandler webSocketHandler, String uriTemplate, Object... uriVariables) {
        return null;
    }

    @Override
    public CompletableFuture<WebSocketSession> execute(WebSocketHandler webSocketHandler, WebSocketHttpHeaders headers, URI uri) {
        return null;
    }
}
