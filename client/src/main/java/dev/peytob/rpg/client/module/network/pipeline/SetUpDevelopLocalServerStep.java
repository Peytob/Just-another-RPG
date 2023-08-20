package dev.peytob.rpg.client.module.network.pipeline;

import dev.peytob.rpg.client.module.network.model.ServerConnectionDetails;
import dev.peytob.rpg.client.module.network.service.grpc.managment.RpcNetworkManager;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import org.springframework.stereotype.Component;

@Component
public class SetUpDevelopLocalServerStep implements InitializingPipelineStep {

    private final RpcNetworkManager rpcNetworkManager;

    public SetUpDevelopLocalServerStep(RpcNetworkManager rpcNetworkManager) {
        this.rpcNetworkManager = rpcNetworkManager;
    }

    @Override
    public void execute() {
        ServerConnectionDetails serverConnectionDetails = new ServerConnectionDetails("dev server", "localhost", "localhost", 9090);
        // TODO Потом будет перенесено в нормальное место, вероятно
        try {
            rpcNetworkManager.loginOnServer("mock", "dev", serverConnectionDetails).get();
        } catch (Exception e) {
            throw new IllegalStateException("Exception while login on server", e);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
