package dev.peytob.rpg.client.module.control.state.event;

import dev.peytob.rpg.client.module.control.context.system.PlayerMovingSystem;
import dev.peytob.rpg.client.module.control.context.system.WindowCloseButtonHandlingSystem;
import dev.peytob.rpg.client.state.InGameEngineState;
import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.engine.context.system.SystemFactory;
import dev.peytob.rpg.engine.state.event.StateSetUpEventHandler;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.INPUT_HANDLING;

@Component
public class CreateControlSystemsStateUpHandler extends StateSetUpEventHandler<InGameEngineState> {

    private final SystemFactory systemFactory;

    public CreateControlSystemsStateUpHandler(SystemFactory systemFactory) {
        this.systemFactory = systemFactory;
    }

    @Override
    public void onStateSetUp(InGameEngineState engineState, EcsContextBuilder contextBuilder) {
        contextBuilder
            .addSystem(systemFactory.getSystem(PlayerMovingSystem.class), INPUT_HANDLING)
            .addSystem(systemFactory.getSystem(WindowCloseButtonHandlingSystem.class), INPUT_HANDLING);
    }
}
