package dev.peytob.rpg.client.system.hid;

import dev.peytob.rpg.client.fsm.annotation.IncludeInAllStates;
import dev.peytob.rpg.client.input.hid.event.HidEvent;
import dev.peytob.rpg.client.input.hid.service.GlfwHidEventQueue;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

@Component
@IncludeInAllStates(order = Integer.MIN_VALUE)
public class LoadWindowEventsSystem implements System {

    private final GlfwHidEventQueue glfwHidEventQueue;

    public LoadWindowEventsSystem(GlfwHidEventQueue glfwHidEventQueue) {
        this.glfwHidEventQueue = glfwHidEventQueue;
    }

    @Override
    public void execute(EcsContext context) {
        while (!glfwHidEventQueue.isEventQueueEmpty()) {
            HidEvent hidEvent = glfwHidEventQueue.pollEvent();
            context.addEvent(hidEvent);
        }
    }
}
