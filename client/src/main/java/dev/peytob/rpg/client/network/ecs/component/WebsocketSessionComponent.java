package dev.peytob.rpg.client.network.ecs.component;

import dev.peytob.rpg.ecs.component.SingletonComponent;
import org.springframework.web.socket.WebSocketSession;

public class WebsocketSessionComponent implements SingletonComponent {

    private final WebSocketSession webSocketSession;

    public WebsocketSessionComponent(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }

    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }
}
