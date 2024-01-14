package dev.peytob.rpg.server.network.websocket.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
@RequiredArgsConstructor
public class ContextSyncWebSocketHandler extends BaseWebSocketHandler<Object> {

    @Override
    public String getPath() {
        return "/sync/context";
    }

    @Override
    protected void handleDeserializedMessage(WebSocketSession session, Object message) {
    }
}
