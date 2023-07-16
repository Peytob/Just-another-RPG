package dev.peytob.rpg.client.module.control.context.system;

import dev.peytob.rpg.client.fsm.annotation.IncludeInState;
import dev.peytob.rpg.client.fsm.state.instance.InGameEngineState;
import dev.peytob.rpg.client.module.control.context.event.KeyboardKeyEvent;
import dev.peytob.rpg.client.module.control.context.event.ScreenshotEvent;
import dev.peytob.rpg.client.module.control.model.KeyAction;
import dev.peytob.rpg.client.module.graphic.service.utils.ImageIOService;
import dev.peytob.rpg.client.module.graphic.service.vendor.ScreenshotService;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import org.lwjgl.glfw.GLFW;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.INPUT_HANDLING;

@Component
@IncludeInState(state = InGameEngineState.class, order = INPUT_HANDLING)
public class ScreenshotHandlerSystem implements System {

    private static final Integer SCREENSHOT_KEY_SCANCODE = GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_P);

    private final ScreenshotService screenshotService;

    private final ImageIOService imageIOService;

    public ScreenshotHandlerSystem(ScreenshotService screenshotService, ImageIOService imageIOService) {
        this.screenshotService = screenshotService;
        this.imageIOService = imageIOService;
    }

    @Override
    public void execute(EcsContext context) {
        context.getEventsByType(KeyboardKeyEvent.class).stream()
            .filter(event -> event.key().scancode().equals(SCREENSHOT_KEY_SCANCODE) && event.action().equals(KeyAction.PRESS))
            .findFirst()
            .ifPresent(event -> context.addEvent(new ScreenshotEvent()));
    }
}
