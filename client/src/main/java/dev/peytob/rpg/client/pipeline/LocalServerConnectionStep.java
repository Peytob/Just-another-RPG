package dev.peytob.rpg.client.pipeline;

import dev.peytob.rpg.client.network.model.ServerCredentials;
import dev.peytob.rpg.client.network.model.ServerDetails;
import dev.peytob.rpg.client.network.service.NetworkManager;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class LocalServerConnectionStep implements InitializingPipelineStep {

    private final NetworkManager networkManager;

    public LocalServerConnectionStep(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    @Override
    public void execute() {
        ServerCredentials serverCredentials = new ServerCredentials("a", "b");
        ServerDetails serverDetails = new ServerDetails("localhost", 9090);
        try {
            networkManager.refreshConnection(serverDetails, serverCredentials).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
