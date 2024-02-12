package dev.peytob.rpg.client.graphic.ecs.system;

import dev.peytob.rpg.client.fsm.annotation.IncludeInAllStates;
import dev.peytob.rpg.client.graphic.model.glfw.Window;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.AFTER_ALL;

@IncludeInAllStates(order = AFTER_ALL - 1)
@RequiredArgsConstructor
@Component
public class WindowSwapBuffersSystem implements System {


    private final Window window;

    @Override
    public void execute(EcsContext context) {
        window.display();
    }
}
