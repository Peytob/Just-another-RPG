package dev.peytob.rpg.client.context.initializer.entity;

import dev.peytob.rpg.client.component.WindowComponent;
import dev.peytob.rpg.client.model.graphics.Window;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.engine.context.initializer.entity.SystemEntityComponentInitializer;
import org.springframework.stereotype.Component;

@Component
public class WindowEntityComponentInitializer implements SystemEntityComponentInitializer {

    private final Window mainWindow;

    public WindowEntityComponentInitializer(Window mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Override
    public void inject(Entity entity) {
        WindowComponent windowComponent = new WindowComponent(mainWindow);
        entity.bindComponent(windowComponent);
    }

    @Override
    public String getId() {
        return "MainWindow";
    }
}
