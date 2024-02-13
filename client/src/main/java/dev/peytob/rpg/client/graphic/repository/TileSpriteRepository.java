package dev.peytob.rpg.client.graphic.repository;

import dev.peytob.rpg.client.graphic.resource.TileSprite;
import dev.peytob.rpg.core.gameplay.resource.Tile;
import dev.peytob.rpg.engine.repositry.BaseRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TileSpriteRepository extends BaseRepository<TileSprite> {

    private final TileIndex tileIndex;

    public TileSpriteRepository() {
        this.tileIndex = new TileIndex();
        this.registerIndex(this.tileIndex);
    }

    public Optional<TileSprite> findByTile(Tile tile) {
        return Optional.ofNullable(tileIndex.getSingle(tile));
    }

    private class TileIndex extends RepositoryIndex<Tile> {
        @Override
        protected Tile extractKey(TileSprite resource) {
            return resource.tile();
        }
    }
}
