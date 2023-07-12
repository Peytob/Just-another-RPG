package dev.peytob.rpg.client.module.control.state.event;

import dev.peytob.rpg.client.module.control.context.system.PlayerMovingSystem;
import dev.peytob.rpg.client.module.control.context.system.ScreenshotHandlerSystem;
import dev.peytob.rpg.client.module.control.context.system.WindowCloseButtonHandlingSystem;
import dev.peytob.rpg.client.fsm.state.instance.InGameEngineState;
import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.engine.context.system.SystemFactory;
import dev.peytob.rpg.client.fsm.event.instance.StateSetUpEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.INPUT_HANDLING;

@Component
public class CreateControlSystemsStateUpHandler {

    private final SystemFactory systemFactory;

    private final InGameEngineState inGameEngineState;

    public CreateControlSystemsStateUpHandler(SystemFactory systemFactory, InGameEngineState inGameEngineState) {
        this.systemFactory = systemFactory;
        this.inGameEngineState = inGameEngineState;
    }

    @EventListener
    public void createInGameControlSystems(StateSetUpEvent event) {
        if (event.engineState() != inGameEngineState) {
            return;
        }

        EcsContextBuilder contextBuilder = event.contextBuilder();

        contextBuilder
            .addSystem(systemFactory.getSystem(PlayerMovingSystem.class), INPUT_HANDLING)
            .addSystem(systemFactory.getSystem(ScreenshotHandlerSystem.class), INPUT_HANDLING)
            .addSystem(systemFactory.getSystem(WindowCloseButtonHandlingSystem.class), INPUT_HANDLING);
    }
}
