package dev.peytob.rpg.client.component;

import dev.peytob.rpg.client.model.graphics.Window;
import dev.peytob.rpg.ecs.component.Component;

public record WindowComponent(Window mainWindow) implements Component {
}
