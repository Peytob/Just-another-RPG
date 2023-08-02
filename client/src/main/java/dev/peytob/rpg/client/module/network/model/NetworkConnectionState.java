package dev.peytob.rpg.client.module.network.model;

import org.springframework.stereotype.Component;

@Component
public class NetworkConnectionState {

    private ServerConnectionDetails serverConnectionDetails;

    private ServerAuth serverAuth;

    public ServerConnectionDetails getServerConnectionDetails() {
        return serverConnectionDetails;
    }

    public void setServerConnectionDetails(ServerConnectionDetails serverConnectionDetails) {
        this.serverConnectionDetails = serverConnectionDetails;
    }

    public ServerAuth getServerAuth() {
        return serverAuth;
    }

    public void setServerAuth(ServerAuth serverAuth) {
        this.serverAuth = serverAuth;
    }

    public boolean isConnectedToServer() {
        return serverConnectionDetails != null;
    }
}
