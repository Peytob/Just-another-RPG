package dev.peytob.rpg.client.network.model;

import dev.peytob.rpg.rpc.interfaces.base.gameplay.ClientEventsDto;
import io.grpc.stub.StreamObserver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class NetworkConnectionState {

    private ServerDetails connectedServer;

    private String serverToken;

    private StreamObserver<ClientEventsDto> networkStreamObserver;
}
