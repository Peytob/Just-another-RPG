package dev.peytob.rpg.client.network.pipeline;

import dev.peytob.rpg.client.network.model.NetworkScheme;
import dev.peytob.rpg.client.network.model.ServerDetails;
import dev.peytob.rpg.client.network.service.NetworkManager;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TestConnectionInitializingStep implements InitializingPipelineStep {

    private final NetworkManager networkManager;

    @Override
    public void execute() {
        ServerDetails serverDetails = new ServerDetails("localhost", 8082, NetworkScheme.HTTP);
        networkManager.loginToServer("localadmin", "pAssW0rd!", serverDetails);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
