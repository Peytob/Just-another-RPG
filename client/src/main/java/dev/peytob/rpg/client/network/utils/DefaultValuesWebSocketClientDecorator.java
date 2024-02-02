package dev.peytob.rpg.client.network.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class DefaultValuesWebSocketClientDecorator implements WebSocketClient {

    private final WebSocketClient webSocketClient;

    private final String rootUri;

    private final WebSocketHttpHeaders defaultHeaders;

    @Override
    public CompletableFuture<WebSocketSession> execute(WebSocketHandler webSocketHandler, String uriTemplate, Object... uriVariables) {
        URI uri = UriComponentsBuilder.fromUriString(rootUri)
            .path(uriTemplate)
            .buildAndExpand(uriVariables)
            .encode()
            .toUri();

        return executeInternal(webSocketHandler, defaultHeaders, uri);
    }

    @Override
    public CompletableFuture<WebSocketSession> execute(WebSocketHandler webSocketHandler, WebSocketHttpHeaders headers, URI uri) {
        URI preparedUri = UriComponentsBuilder.fromUriString(rootUri)
            .path(uri.toString())
            .build(true)
            .toUri();

        WebSocketHttpHeaders mergedHeaders = mergeHeaders(defaultHeaders, headers);

        return executeInternal(webSocketHandler, mergedHeaders, preparedUri);
    }

    private CompletableFuture<WebSocketSession> executeInternal(WebSocketHandler webSocketHandler, WebSocketHttpHeaders headers, URI uri) {
        return webSocketClient.execute(webSocketHandler, headers, uri);
    }

    private WebSocketHttpHeaders mergeHeaders(WebSocketHttpHeaders firstHeaders, WebSocketHttpHeaders secondHeaders) {
        WebSocketHttpHeaders targetHeaders = new WebSocketHttpHeaders();
        targetHeaders.putAll(firstHeaders);
        targetHeaders.putAll(secondHeaders);
        return targetHeaders;
    }
}
