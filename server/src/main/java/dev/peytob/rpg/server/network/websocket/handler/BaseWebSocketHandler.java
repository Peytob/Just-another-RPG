package dev.peytob.rpg.server.network.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public abstract class BaseWebSocketHandler<T> extends TextWebSocketHandler {

    @SuppressWarnings("unchecked")
    private final Class<T> messageType = (Class<T>) GenericTypeResolver.resolveTypeArgument(this.getClass(), BaseWebSocketHandler.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        T deserializedMessage = objectMapper.readValue(message.getPayload(), messageType);
        handleDeserializedMessage(session, deserializedMessage);
    }

    abstract protected T handleDeserializedMessage(WebSocketSession session, T message);
}
