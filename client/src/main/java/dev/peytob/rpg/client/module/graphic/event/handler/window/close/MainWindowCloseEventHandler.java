package dev.peytob.rpg.client.module.graphic.event.handler.window.close;

import dev.peytob.rpg.client.module.graphic.model.Window;
import org.springframework.stereotype.Component;

@Component
public final class MainWindowCloseEventHandler extends WindowCloseEventHandler {

    private final Window window;

    public MainWindowCloseEventHandler(Window window) {
        this.window = window;
    }

    @Override
    public void handle() {
        window.close();
    }
}
