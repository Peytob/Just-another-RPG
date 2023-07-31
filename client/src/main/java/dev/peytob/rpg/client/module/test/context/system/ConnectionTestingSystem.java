package dev.peytob.rpg.client.module.test.context.system;

import dev.peytob.rpg.client.fsm.annotation.IncludeInState;
import dev.peytob.rpg.client.fsm.state.instance.InGameLoadingState;
import dev.peytob.rpg.client.module.network.model.ServerConnectionDetails;
import dev.peytob.rpg.client.module.network.service.ServerDetailsService;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

@IncludeInState(state = InGameLoadingState.class)
@Component
public class ConnectionTestingSystem implements System {

    private final ServerDetailsService serverDetailsService;

    private boolean isSystemEnabled;

    public ConnectionTestingSystem(ServerDetailsService serverDetailsService) {
        this.serverDetailsService = serverDetailsService;
        this.isSystemEnabled = true;
    }

    @Override
    public void execute(EcsContext context) {
        if (isSystemEnabled) {
            try {
                ServerConnectionDetails serverConnectionDetails = serverDetailsService.getServerConnectionDetails().get();
                java.lang.System.out.println(serverConnectionDetails);
            } catch (Exception exception) {
                java.lang.System.out.println("Server is not available...");
                exception.printStackTrace();
            } finally {
                isSystemEnabled = false;
            }
        }
    }
}
