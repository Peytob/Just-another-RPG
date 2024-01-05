package dev.peytob.rpg.client.network.pipeline;

import dev.peytob.rpg.client.network.model.ServerCredentials;
import dev.peytob.rpg.client.network.model.ServerDetails;
import dev.peytob.rpg.client.network.service.CharacterServerInteractionService;
import dev.peytob.rpg.client.network.service.NetworkManager;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class LocalServerConnectionStep implements InitializingPipelineStep {

    private final NetworkManager networkManager;

    private final CharacterServerInteractionService characterServerInteractionService;

    @Override
    public void execute() {
        ServerCredentials serverCredentials = new ServerCredentials("string", "pAssW0rd!");
        ServerDetails serverDetails = new ServerDetails("localhost", 9090);

        try {
            networkManager.refreshConnection(serverDetails, serverCredentials).get();
            characterServerInteractionService.enterToServer("m847g7bnsh63psox").get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
