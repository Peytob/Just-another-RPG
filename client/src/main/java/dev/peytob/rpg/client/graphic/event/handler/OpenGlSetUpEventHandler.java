package dev.peytob.rpg.client.graphic.event.handler;

import dev.peytob.rpg.client.fsm.event.instance.BeforeInitializingPipelineExecuteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static org.lwjgl.opengl.GL11C.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class OpenGlSetUpEventHandler {

    @EventListener
    void renderingContextCreate(BeforeInitializingPipelineExecuteEvent ignored) {
        log.info("Enabling opengl blend");
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }
}
