package dev.peytob.rpg.client.network.service;

import dev.peytob.rpg.client.network.model.ServerDetails;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.WebSocketClient;

public interface NetworkManager {

    void loginToServer(String username, String password, ServerDetails serverDetails);

    void logoutFromServer();

    WebSocketClient getWebsocketClient();

    RestTemplate getRestTemplateClient();

    ServerDetails getServerDetails();

    boolean isConnectedToServer();
}
