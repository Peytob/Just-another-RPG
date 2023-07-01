package dev.peytob.rpg.client.state.event.handler;

import dev.peytob.rpg.client.ClientEngine;
import dev.peytob.rpg.engine.state.event.instance.ChangeStateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ChangeStateEventHandler {

    private final ClientEngine clientEngine;

    public ChangeStateEventHandler(ClientEngine clientEngine) {
        this.clientEngine = clientEngine;
    }

    @EventListener
    public void onEngineStateChanged(ChangeStateEvent changeStateEvent) {
        clientEngine.updateEngineState(changeStateEvent.nextEngineState());
    }
}
