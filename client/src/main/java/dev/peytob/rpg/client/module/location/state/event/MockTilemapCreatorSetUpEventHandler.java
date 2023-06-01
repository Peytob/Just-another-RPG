package dev.peytob.rpg.client.module.location.state.event;

import dev.peytob.rpg.client.module.graphic.model.RenderableTilemap;
import dev.peytob.rpg.client.state.InGameEngineState;
import dev.peytob.rpg.client.module.graphic.context.component.TilemapComponent;
import dev.peytob.rpg.core.module.location.model.tilemap.Tilemap;
import dev.peytob.rpg.core.module.location.model.tilemap.Tilemaps;
import dev.peytob.rpg.core.module.location.repository.TileRepository;
import dev.peytob.rpg.core.module.location.resource.Tile;
import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.engine.state.event.handler.StateSetUpEventHandler;
import dev.peytob.rpg.math.vector.Vectors;
import org.springframework.stereotype.Component;

@Component
public class MockTilemapCreatorSetUpEventHandler extends StateSetUpEventHandler<InGameEngineState> {

    private final TileRepository tileRepository;

    public MockTilemapCreatorSetUpEventHandler(TileRepository tileRepository) {
        this.tileRepository = tileRepository;
    }

    @Override
    public void onStateSetUp(InGameEngineState engineState, EcsContextBuilder contextBuilder) {
        Tilemap tilemap = Tilemaps.mutable(Vectors.immutableVec2i(16, 16));
        Tile exampleTile = new Tile(100, "test_tile");
        tileRepository.append(exampleTile);

        for (int x = 0; x < tilemap.getSizes().x(); x++) {
            for (int y = 0; y < tilemap.getSizes().y(); y++) {
                tilemap.setTile(x, y, exampleTile);
            }
        }

        RenderableTilemap renderableTilemap = new RenderableTilemap(tilemap, Vectors.immutableVec2i(64, 64));

        contextBuilder.initializeEntity((entity, ecsContext) ->
                entity.bindComponent(new TilemapComponent(renderableTilemap)));
    }
}
