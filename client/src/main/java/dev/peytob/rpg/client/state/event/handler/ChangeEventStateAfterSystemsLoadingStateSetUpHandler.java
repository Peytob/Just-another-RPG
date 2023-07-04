package dev.peytob.rpg.client.state.event.handler;

import dev.peytob.rpg.client.context.system.ChangeGameStateAfterLoadingSystem;
import dev.peytob.rpg.client.state.InGameLoadingState;
import dev.peytob.rpg.engine.context.system.SystemFactory;
import dev.peytob.rpg.engine.state.event.instance.StateSetUpEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.AFTER_ALL;

@Component
public class ChangeEventStateAfterSystemsLoadingStateSetUpHandler {

    private final InGameLoadingState inGameLoadingState;

    private final SystemFactory systemFactory;

    public ChangeEventStateAfterSystemsLoadingStateSetUpHandler(InGameLoadingState inGameLoadingState, SystemFactory systemFactory) {
        this.inGameLoadingState = inGameLoadingState;
        this.systemFactory = systemFactory;
    }

    @EventListener
    void changeEventStateAfterSystemsLoaded(StateSetUpEvent stateSetUpEvent) {
        if (!stateSetUpEvent.engineState().equals(inGameLoadingState)) {
            return;
        }

        stateSetUpEvent.contextBuilder()
            .addSystem(systemFactory.getSystem(ChangeGameStateAfterLoadingSystem.class), AFTER_ALL);
    }
}
