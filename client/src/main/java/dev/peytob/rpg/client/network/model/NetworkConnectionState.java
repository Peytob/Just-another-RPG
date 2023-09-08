package dev.peytob.rpg.client.network.model;

import org.springframework.stereotype.Component;

@Component
public class NetworkConnectionState {

    private ServerDetails connectedServer;

    private String serverToken;

    public ServerDetails getConnectedServer() {
        return connectedServer;
    }

    public void setConnectedServer(ServerDetails connectedServer) {
        this.connectedServer = connectedServer;
    }

    public String getServerToken() {
        return serverToken;
    }

    public void setServerToken(String serverToken) {
        this.serverToken = serverToken;
    }

    public boolean isConnectedToServer() {
        return serverToken != null;
    }
}
