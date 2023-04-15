package dev.peytob.rpg.client.state.provider;

import dev.peytob.rpg.client.context.system.control.WindowCloseButtonHandlingSystem;
import dev.peytob.rpg.client.context.system.graphic.rendering.FramebufferClearSystem;
import dev.peytob.rpg.client.context.system.graphic.rendering.MeshRenderingSystem;
import dev.peytob.rpg.client.context.system.graphic.window.WindowClosingHandlingSystem;
import dev.peytob.rpg.client.context.system.graphic.window.WindowEventPoolingSystem;
import dev.peytob.rpg.client.context.system.graphic.window.WindowSwappingBuffersSystem;
import dev.peytob.rpg.engine.context.system.SystemFactory;
import dev.peytob.rpg.engine.state.EngineState;
import dev.peytob.rpg.engine.state.EngineStateBuilder;
import dev.peytob.rpg.engine.state.EngineStateProvider;
import org.springframework.stereotype.Component;

@Component
public final class InGameStateProvider implements EngineStateProvider {

    private final SystemFactory systemFactory;

    public InGameStateProvider(SystemFactory systemFactory) {
        this.systemFactory = systemFactory;
    }

    @Override
    public EngineState createEngineState() {
        return new EngineStateBuilder()
            .setName("InGame")
            .nextSystem(systemFactory.getSystem(WindowEventPoolingSystem.class))

            .nextSystem(systemFactory.getSystem(WindowCloseButtonHandlingSystem.class))

            .nextSystem(systemFactory.getSystem(FramebufferClearSystem.class))
            .nextSystem(systemFactory.getSystem(MeshRenderingSystem.class))
            .nextSystem(systemFactory.getSystem(WindowSwappingBuffersSystem.class))

            .nextSystem(systemFactory.getSystem(WindowClosingHandlingSystem.class))
            .build();
    }
}
