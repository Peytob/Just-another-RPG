package dev.peytob.rpg.client.network.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.WebSocketClient;

@Getter
@Setter
@Builder
public class NetworkConnectionState {

    private ServerDetails serverDetails;

    private String authorizationToken;

    private WebSocketClient webSocketClient;

    private RestTemplate restTemplate;
}
