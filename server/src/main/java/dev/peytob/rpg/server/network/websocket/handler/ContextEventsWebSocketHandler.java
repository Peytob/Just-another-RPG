package dev.peytob.rpg.server.network.websocket.handler;

import dev.peytob.rpg.core.network.model.client.ClientEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

@Component
public class ContextEventsWebSocketHandler extends BaseWebSocketHandler<ClientEvent> {

    @Override
    public String getPath() {
        return "/context/events";
    }

    @Override
    protected void handleDeserializedMessage(WebSocketSession session, ClientEvent message) {
        System.out.println(message);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("afterConnectionEstablished");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("afterConnectionClosed");
    }
}
