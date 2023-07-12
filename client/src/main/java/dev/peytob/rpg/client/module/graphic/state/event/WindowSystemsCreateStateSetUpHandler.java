package dev.peytob.rpg.client.module.graphic.state.event;

import dev.peytob.rpg.client.module.graphic.context.system.window.WindowClosingHandlingSystem;
import dev.peytob.rpg.client.module.graphic.context.system.window.WindowEventPoolingSystem;
import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.engine.context.system.SystemFactory;
import dev.peytob.rpg.client.fsm.event.instance.StateSetUpEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.AFTER_ALL;
import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.UPDATE_POOLING;

@Component
public class WindowSystemsCreateStateSetUpHandler {

    private final SystemFactory systemFactory;

    public WindowSystemsCreateStateSetUpHandler(SystemFactory systemFactory) {
        this.systemFactory = systemFactory;
    }

    @EventListener
    public void onStateSetUp(StateSetUpEvent stateSetUpEvent) {
        EcsContextBuilder contextBuilder = stateSetUpEvent.contextBuilder();

        contextBuilder
            .addSystem(systemFactory.getSystem(WindowEventPoolingSystem.class), UPDATE_POOLING)
            .addSystem(systemFactory.getSystem(WindowClosingHandlingSystem.class), AFTER_ALL);
    }
}
