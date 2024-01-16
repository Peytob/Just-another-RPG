package dev.peytob.rpg.client.network.service;

import dev.peytob.rpg.client.network.model.NetworkConnectionState;
import dev.peytob.rpg.client.network.model.ServerDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

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
    public void loginToServer(String login, String password, ServerDetails serverDetails) {
        log.info("Trying to connect to server {}", serverDetails.host());

        String rootUri = UriComponentsBuilder.newInstance()
            .scheme(serverDetails.networkScheme().name())
            .host(serverDetails.host())
            .port(serverDetails.port())
            .path("/")
            .toUriString();

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
            .rootUri(rootUri);

        String token = authService.login(login, password, restTemplateBuilder.build());

        RestTemplate restTemplate = restTemplateBuilder.defaultHeader("Authorization", token).build();

        WebSocketClient webSocketClient = new StandardWebSocketClient();

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
        authService.logout();
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
