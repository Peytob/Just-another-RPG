package dev.peytob.rpg.client.module.graphic.pipeline;

import dev.peytob.rpg.client.module.graphic.model.Image;
import dev.peytob.rpg.client.module.graphic.resource.Texture;
import dev.peytob.rpg.client.module.graphic.resource.TextureAtlas;
import dev.peytob.rpg.client.module.graphic.service.utils.ImageLoader;
import dev.peytob.rpg.client.module.graphic.service.vendor.TextureService;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import dev.peytob.rpg.math.vector.Vec2i;
import org.springframework.stereotype.Component;

import java.util.Map;

import static dev.peytob.rpg.client.module.graphic.model.DefaultTexturesIds.DEFAULT_TILE_ATLAS_ID;
import static dev.peytob.rpg.math.vector.Vectors.immutableVec2i;
import static org.lwjgl.opengl.GL11.GL_RGBA8;

@Component
public class TextureAtlasLoadingStep implements InitializingPipelineStep {

    private static final String TILES_DIRECTORY = "/images/tiles";

    private final ImageLoader imageLoader;

    private final TextureService textureService;

    public TextureAtlasLoadingStep(ImageLoader imageLoader, TextureService textureService) {
        this.imageLoader = imageLoader;
        this.textureService = textureService;
    }

    @Override
    public void execute() {
        Image testTile = imageLoader.loaderClasspathImage(TILES_DIRECTORY + "/test_tile.png");
        Texture texture = textureService.createTexture(DEFAULT_TILE_ATLAS_ID + "_texture", GL_RGBA8, testTile);
        Vec2i tileSize = immutableVec2i(160, 160);
        Map<String, TextureAtlas.Sprite> atlasSprites = Map.of(
                "test_tile1", new TextureAtlas.Sprite(0, immutableVec2i(0, 0), tileSize),
                "test_tile2", new TextureAtlas.Sprite(1, immutableVec2i(160, 0), tileSize),
                "test_tile3", new TextureAtlas.Sprite(2, immutableVec2i(0, 160), tileSize),
                "test_tile4", new TextureAtlas.Sprite(3, immutableVec2i(160, 160), tileSize)
        );

        textureService.wrapTextureAsAtlas(DEFAULT_TILE_ATLAS_ID, texture, atlasSprites);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
