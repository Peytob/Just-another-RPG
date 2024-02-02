package dev.peytob.rpg.server.network.websocket.configuration;

import dev.peytob.rpg.server.network.websocket.handler.BaseWebSocketHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.Collection;

@Configuration
@EnableWebSocket
@AllArgsConstructor
@Slf4j
public class WebSocketConfiguration implements WebSocketConfigurer {

    private final Collection<BaseWebSocketHandler<?>> webSocketHandlerCollection;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        if (webSocketHandlerCollection.isEmpty()) {
            log.info("Websocket game handlers not found, skipping registration");
            return;
        }

        log.info("Registering {} game websocket handlers", webSocketHandlerCollection.size());

        webSocketHandlerCollection.forEach(handler -> {
            log.info("Registering game websocket handler '{}' with path '{}'", handler.getClass().getSimpleName(), handler.getPath());
            registry.addHandler(handler, handler.getPath());
        });

        log.info("Game websocket handlers has been registered");
    }
}
