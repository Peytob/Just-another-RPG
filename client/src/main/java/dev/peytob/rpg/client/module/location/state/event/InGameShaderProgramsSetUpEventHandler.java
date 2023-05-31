package dev.peytob.rpg.client.module.location.state.event;

import dev.peytob.rpg.client.module.graphic.service.facade.shaderprogram.TilemapShaderProgramFacade;
import dev.peytob.rpg.client.state.InGameEngineState;
import dev.peytob.rpg.core.module.location.context.component.TilemapComponent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.engine.state.event.handler.AfterStateSetUpEventHandler;
import dev.peytob.rpg.math.vector.Vectors;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(0)
public class InGameShaderProgramsSetUpEventHandler extends AfterStateSetUpEventHandler<InGameEngineState> {

    private final TilemapShaderProgramFacade tilemapShaderProgramFacade;

    public InGameShaderProgramsSetUpEventHandler(TilemapShaderProgramFacade tilemapShaderProgramFacade) {
        this.tilemapShaderProgramFacade = tilemapShaderProgramFacade;
    }

    @Override
    public void handleStateTearDown(InGameEngineState engineState, EcsContext newContext) {
        newContext.getSingletonComponentByType(TilemapComponent.class).ifPresent(this::setUpTilemapShader);
    }

    private void setUpTilemapShader(TilemapComponent tilemapComponent) {
        tilemapShaderProgramFacade.setTilemapSizes(tilemapComponent.getTilemap().getSizes());
        // TODO Make not hardcoded
        tilemapShaderProgramFacade.setTileSizes(Vectors.immutableVec2i(32, 32));
    }
}
