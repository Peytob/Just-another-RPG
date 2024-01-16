package dev.peytob.rpg.server.network.websocket.handler;

import dev.peytob.rpg.core.network.model.client.ClientEvent;
import dev.peytob.rpg.server.network.dto.WorldContextEnterDto;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class ContextEventsWebSocketHandler extends BaseWebSocketHandler<ClientEvent> {

    @Override
    public String getPath() {
        return "/context/events";
    }

    @Override
    protected void handleDeserializedMessage(WebSocketSession session, ClientEvent message) {
    }
}
