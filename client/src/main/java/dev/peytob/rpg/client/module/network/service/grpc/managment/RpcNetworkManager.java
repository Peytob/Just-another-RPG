package dev.peytob.rpg.client.module.network.service.grpc.managment;

import dev.peytob.rpg.client.module.network.model.ServerAuth;
import dev.peytob.rpg.client.module.network.model.ServerConnectionDetails;

import java.util.concurrent.CompletableFuture;

public interface RpcNetworkManager {

    CompletableFuture<ServerAuth> loginOnServer(String username, String password, ServerConnectionDetails serverConnectionDetails);

    CompletableFuture<ServerConnectionDetails> logoutFromServer();

    ServerAuth getServerAuth();

    boolean isConnectedToServer();
}
