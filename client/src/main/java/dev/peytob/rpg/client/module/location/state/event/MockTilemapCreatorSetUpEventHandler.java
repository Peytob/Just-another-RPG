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
        Tile tile1 = new Tile(1, "test_tile1");
        tileRepository.append(tile1);
        Tile tile2 = new Tile(2, "test_tile2");
        tileRepository.append(tile2);
        Tile tile3 = new Tile(3, "test_tile3");
        tileRepository.append(tile3);
        Tile tile4 = new Tile(4, "test_tile4");
        tileRepository.append(tile4);

        for (int x = 0, tileId = 0; x < tilemap.getSizes().x(); x++) {
            for (int y = 0; y < tilemap.getSizes().y(); y++) {
                Tile tile = tileRepository.getById(tileId);
                tileId++;
                tilemap.setTile(x, y, tile);
            }
        }

        RenderableTilemap renderableTilemap = new RenderableTilemap(tilemap, Vectors.immutableVec2i(64, 64));

        contextBuilder.initializeEntity((entity, ecsContext) ->
                entity.bindComponent(new TilemapComponent(renderableTilemap)));
    }
}
