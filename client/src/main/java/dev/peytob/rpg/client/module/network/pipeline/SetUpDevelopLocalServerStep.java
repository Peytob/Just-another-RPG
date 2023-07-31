package dev.peytob.rpg.client.module.network.pipeline;

import dev.peytob.rpg.client.module.network.service.grpc.managment.RpcNetworkManager;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

@Component
public class SetUpDevelopLocalServerStep implements InitializingPipelineStep {

    private final RpcNetworkManager rpcNetworkManager;

    public SetUpDevelopLocalServerStep(RpcNetworkManager rpcNetworkManager) {
        this.rpcNetworkManager = rpcNetworkManager;
    }

    @Override
    public void execute() {
        Channel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
            .usePlaintext()
            .build();

        rpcNetworkManager.connectServices(channel);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}