package dev.peytob.rpg.client.module.graphic.event.handler.window.close;

import dev.peytob.rpg.client.module.graphic.context.event.window.WindowCloseEvent;
import dev.peytob.rpg.client.module.graphic.model.Window;
import dev.peytob.rpg.engine.event.EventHandler;
import org.springframework.stereotype.Component;

@Component
public final class MainWindowCloseEventHandler implements EventHandler<WindowCloseEvent> {

    private final Window window;

    public MainWindowCloseEventHandler(Window window) {
        this.window = window;
    }

    @Override
    public void handleEvent(WindowCloseEvent event) {
        window.close();
    }
}
