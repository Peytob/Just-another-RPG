package dev.peytob.rpg.client.module.network.service.grpc.managment;

import dev.peytob.rpg.client.module.network.model.ServerAuth;
import dev.peytob.rpg.client.module.network.model.ServerConnectionDetails;

import java.util.concurrent.Future;

public interface RpcNetworkManager {
    Future<ServerAuth> loginOnServer(String username, String password, ServerConnectionDetails serverConnectionDetails);

    Future<ServerConnectionDetails> logoutFromServer();

    ServerAuth getServerAuth();

    boolean isConnectedToServer();
}
