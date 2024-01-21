package dev.peytob.rpg.client.network.service;

import dev.peytob.rpg.client.network.model.NetworkConnectionState;
import dev.peytob.rpg.client.network.model.NetworkScheme;
import dev.peytob.rpg.client.network.model.ServerDetails;
import dev.peytob.rpg.client.network.utils.DefaultValuesWebSocketClientDecorator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

import static dev.peytob.rpg.client.network.utils.DefaultHeaders.AUTHORIZATION_HEADER;

@Service
@Slf4j
public class NetworkManagerImpl implements NetworkManager {

    private NetworkConnectionState networkConnectionState;

    private final AuthService authService;

    public NetworkManagerImpl(AuthService authService) {
        this.networkConnectionState = null;
        this.authService = authService;
    }

    @Override
    public void loginToServer(String username, String password, ServerDetails serverDetails) {
        log.info("Trying to connect to server {}", serverDetails.host());

        String httpRootUri = UriComponentsBuilder.newInstance()
            .scheme(serverDetails.networkScheme().name())
            .host(serverDetails.host())
            .port(serverDetails.port())
            .path("/")
            .toUriString();

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
            .rootUri(httpRootUri);

        String token = authService.login(username, password, restTemplateBuilder.build());

        RestTemplate restTemplate = restTemplateBuilder.defaultHeader(AUTHORIZATION_HEADER, token).build();

        String wsRootUri = UriComponentsBuilder.fromUriString(httpRootUri)
            .scheme(NetworkScheme.WEB_SOCKET.name())
            .toUriString();

        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketHttpHeaders defaultWebSocketHttpHeaders = new WebSocketHttpHeaders();
        defaultWebSocketHttpHeaders.set(AUTHORIZATION_HEADER, token);

        webSocketClient = DefaultValuesWebSocketClientDecorator.builder()
            .webSocketClient(webSocketClient)
            .defaultHeaders(defaultWebSocketHttpHeaders)
            .rootUri(wsRootUri)
            .build();

        this.networkConnectionState = NetworkConnectionState.builder()
            .authorizationToken(token)
            .restTemplate(restTemplate)
            .serverDetails(serverDetails)
            .webSocketClient(webSocketClient)
            .build();

        log.info("Connect to server {} was performed", serverDetails.host());
    }

    @Override
    public void logoutFromServer() {
        NetworkConnectionState connectionState = getConnectionState();
        authService.logout(connectionState.getRestTemplate());
        networkConnectionState = null;
    }

    @Override
    public WebSocketClient getWebsocketClient() {
        NetworkConnectionState connectionState = getConnectionState();
        return connectionState.getWebSocketClient();
    }

    @Override
    public RestTemplate getRestTemplateClient() {
        NetworkConnectionState connectionState = getConnectionState();
        return connectionState.getRestTemplate();
    }

    @Override
    public ServerDetails getServerDetails() {
        NetworkConnectionState connectionState = getConnectionState();
        return connectionState.getServerDetails();
    }

    @Override
    public boolean isConnectedToServer() {
        return networkConnectionState != null;
    }

    private NetworkConnectionState getConnectionState() {
        return Objects.requireNonNull(this.networkConnectionState, "Server connection not found");
    }
}
