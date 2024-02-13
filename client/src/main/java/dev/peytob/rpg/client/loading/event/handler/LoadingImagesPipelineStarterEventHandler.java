package dev.peytob.rpg.client.loading.event.handler;

import dev.peytob.rpg.client.fsm.event.instance.AfterEngineStatePushEvent;
import dev.peytob.rpg.client.graphic.model.Image;
import dev.peytob.rpg.client.graphic.repository.TileSpriteRepository;
import dev.peytob.rpg.client.graphic.resource.Sprite;
import dev.peytob.rpg.client.graphic.resource.Texture;
import dev.peytob.rpg.client.graphic.resource.TileSprite;
import dev.peytob.rpg.client.graphic.service.TextureService;
import dev.peytob.rpg.client.loading.service.ImageLoader;
import dev.peytob.rpg.core.gameplay.repository.TileRepository;
import dev.peytob.rpg.core.loading.service.FileStructureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

import static dev.peytob.rpg.math.geometry.Rectangles.rect;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoadingImagesPipelineStarterEventHandler {

    private final TextureService textureService;

    private final ImageLoader imageLoader;

    private final FileStructureService fileStructureService;

    private final TileSpriteRepository tileSpriteRepository;

    private final TileRepository tileRepository;

    @EventListener
    public void execute(AfterEngineStatePushEvent ignored) {
        if (tileSpriteRepository.getCount() != 0) {
            return;
        }

        log.info("Creating mock tile sprites");

        Path testTilesAtlas = fileStructureService.getTexturesPath().resolve("./test_tiles_atlas.png");
        Image image = imageLoader.loadFileImageResource(testTilesAtlas.toString());

        Texture texture = textureService.createTexture("test_tiles_atlas", image);

        // TODO Remove this shit
        tileRepository.getAll().forEach(tile -> {
            Sprite sprite = new Sprite(tile.id() + " -> " + texture.id(), texture, rect(0, 0, 1, 1));
            TileSprite tileSprite = new TileSprite(tile.id() + " -> " + texture.id(), tile, sprite);
            tileSpriteRepository.append(tileSprite);
            log.info("Created mock tile sprite: tile {} -> texture {}", tile.id(), texture.id());
        });
    }
}
