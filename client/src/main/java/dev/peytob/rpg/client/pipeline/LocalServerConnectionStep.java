package dev.peytob.rpg.client.pipeline;

import dev.peytob.rpg.client.network.model.ServerCredentials;
import dev.peytob.rpg.client.network.model.ServerDetails;
import dev.peytob.rpg.client.network.service.NetworkManager;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import org.springframework.stereotype.Component;

@Component
public class LocalServerConnectionStep implements InitializingPipelineStep {

    private final NetworkManager networkManager;

    public LocalServerConnectionStep(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    @Override
    public void execute() {
        networkManager.refreshConnection(new ServerDetails("localhost", 9090), new ServerCredentials("a", "b"));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
