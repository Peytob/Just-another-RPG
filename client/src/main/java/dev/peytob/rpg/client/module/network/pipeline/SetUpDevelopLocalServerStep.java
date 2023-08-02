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
        ServerConnectionDetails serverConnectionDetails = new ServerConnectionDetails("dev server", "localhost", 9090);
        rpcNetworkManager.loginOnServer("mock", "dev", serverConnectionDetails);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
