package dev.peytob.rpg.client.network.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class NetworkConnectionState {

    private ServerDetails connectedServer;

    private String serverToken;
}
