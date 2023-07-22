package dev.peytob.rpg.client.module.base.pipeline.network;

import dev.peytob.rpg.client.module.base.service.network.grpc.DynamicGrpcService;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class SetUpDevelopLocalServerStep implements InitializingPipelineStep {

    private final Collection<DynamicGrpcService> grpcServices;

    public SetUpDevelopLocalServerStep(Collection<DynamicGrpcService> grpcServices) {
        this.grpcServices = grpcServices;
    }

    @Override
    public void execute() {
        Channel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
            .usePlaintext()
            .build();

        grpcServices.forEach(grpcService -> grpcService.changeGrpcChannel(channel));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
