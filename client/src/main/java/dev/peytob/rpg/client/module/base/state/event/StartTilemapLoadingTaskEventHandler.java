package dev.peytob.rpg.client.module.base.state.event;

import dev.peytob.rpg.client.module.base.context.system.loading.TilemapLevelLoadingSystem;
import dev.peytob.rpg.client.fsm.state.instance.InGameLoadingState;
import dev.peytob.rpg.engine.context.system.SystemFactory;
import dev.peytob.rpg.client.fsm.event.instance.StateSetUpEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartTilemapLoadingTaskEventHandler {

    private final InGameLoadingState inGameLoadingState;

    private final SystemFactory systemFactory;

    public StartTilemapLoadingTaskEventHandler(InGameLoadingState inGameLoadingState, SystemFactory systemFactory) {
        this.inGameLoadingState = inGameLoadingState;
        this.systemFactory = systemFactory;
    }

    @EventListener
    void startTilemapLoadingTask(StateSetUpEvent stateSetUpEvent) {
        if (!stateSetUpEvent.engineState().equals(inGameLoadingState)) {
            return;
        }

        stateSetUpEvent.contextBuilder()
            .addSystem(systemFactory.getSystem(TilemapLevelLoadingSystem.class), 0);
    }
}
