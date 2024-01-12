package dev.peytob.rpg.client.network.pipeline;

import dev.peytob.rpg.client.network.model.ServerCredentials;
import dev.peytob.rpg.client.network.model.ServerDetails;
import dev.peytob.rpg.client.network.service.CharacterServerInteractionService;
import dev.peytob.rpg.client.network.service.NetworkManager;
import dev.peytob.rpg.core.network.model.client.ClientEvent;
import dev.peytob.rpg.core.network.model.server.WorldState;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
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
            StreamObserver<Collection<ClientEvent>> collectionStreamObserver = networkManager.startEventsStream(new StreamObserver<WorldState>() {
                @Override
                public void onNext(WorldState value) {
                    System.out.println(value);
                }

                @Override
                public void onError(Throwable t) {
                    System.out.println(t);
                }

                @Override
                public void onCompleted() {
                    System.out.println("Completed!");
                }
            });

            collectionStreamObserver.onNext(Collections.emptyList());
            Thread.sleep(1000);
            collectionStreamObserver.onNext(Collections.emptyList());
            Thread.sleep(1000);
            collectionStreamObserver.onNext(Collections.emptyList());
            Thread.sleep(1000);
            collectionStreamObserver.onCompleted();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
