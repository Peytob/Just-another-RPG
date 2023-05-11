package dev.peytob.rpg.client.state;

import dev.peytob.rpg.client.module.control.context.system.PlayerMovingSystem;
import dev.peytob.rpg.client.module.control.context.system.WindowCloseButtonHandlingSystem;
import dev.peytob.rpg.client.module.graphic.context.system.rendering.CameraUpdatingSystem;
import dev.peytob.rpg.client.module.graphic.context.system.rendering.FramebufferClearSystem;
import dev.peytob.rpg.client.module.graphic.context.system.rendering.TilemapRenderSystem;
import dev.peytob.rpg.client.module.graphic.context.system.window.WindowClosingHandlingSystem;
import dev.peytob.rpg.client.module.graphic.context.system.window.WindowEventPoolingSystem;
import dev.peytob.rpg.client.module.graphic.context.system.window.WindowSwappingBuffersSystem;
import dev.peytob.rpg.core.module.base.context.system.PositionsSyncSystem;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.OrderedSystem;
import dev.peytob.rpg.engine.context.system.SystemFactory;
import dev.peytob.rpg.engine.state.EngineState;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public final class InGameEngineState implements EngineState {

    private final SystemFactory systemFactory;

    public InGameEngineState(SystemFactory systemFactory) {
        this.systemFactory = systemFactory;
    }

    @Override
    public String getName() {
        return "InGame";
    }

    @Override
    public Collection<OrderedSystem> getSystems() {
        // TODO Make builder or something else

        int position = 0;

        return List.of(
            OrderedSystem.wrap(systemFactory.getSystem(WindowEventPoolingSystem.class), position++),

            OrderedSystem.wrap(systemFactory.getSystem(WindowCloseButtonHandlingSystem.class), position++),
            OrderedSystem.wrap(systemFactory.getSystem(PlayerMovingSystem.class), position++),

            OrderedSystem.wrap(systemFactory.getSystem(PositionsSyncSystem.class), position++),

            OrderedSystem.wrap(systemFactory.getSystem(FramebufferClearSystem.class), position++),
            OrderedSystem.wrap(systemFactory.getSystem(CameraUpdatingSystem.class), position++),
            OrderedSystem.wrap(systemFactory.getSystem(TilemapRenderSystem.class), position++),
            OrderedSystem.wrap(systemFactory.getSystem(WindowSwappingBuffersSystem.class), position++),

            OrderedSystem.wrap(systemFactory.getSystem(WindowClosingHandlingSystem.class), position++)
        );
    }

    @Override
    public void onSetUp(EcsContext ecsContext) {
        // Nothing
    }

    @Override
    public void onTearDown(EcsContext ecsContext) {
        // Nothing
    }
}
