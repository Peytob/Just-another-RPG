package dev.peytob.rpg.client.graphic.ecs.system;

import dev.peytob.rpg.client.fsm.annotation.IncludeInState;
import dev.peytob.rpg.client.fsm.state.InGameState;
import dev.peytob.rpg.client.gameplay.ecs.component.WorldComponent;
import dev.peytob.rpg.client.graphic.ecs.component.RenderContextComponent;
import dev.peytob.rpg.client.graphic.model.RenderingContext;
import dev.peytob.rpg.client.graphic.service.TilemapRenderService;
import dev.peytob.rpg.core.gameplay.resource.World;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.RENDERING;

@RequiredArgsConstructor
@IncludeInState(value = InGameState.class, order = RENDERING)
@Component
public class TilemapRenderSystem implements System {

    private final TilemapRenderService tilemapRenderService;

    @Override
    public void execute(EcsContext context) {
        context.getSingletonComponentByType(RenderContextComponent.class)
            .map(RenderContextComponent::getRenderContext)
            .ifPresent(renderingContext -> renderTilemap(context, renderingContext));
    }

    private void renderTilemap(EcsContext ecsContext, RenderingContext renderingContext) {
        ecsContext.getSingletonComponentByType(WorldComponent.class)
            .map(WorldComponent::getWorld)
            .map(World::tilemap)
            .ifPresent(tilemap -> tilemapRenderService.renderTilemap(tilemap, renderingContext));
    }
}
