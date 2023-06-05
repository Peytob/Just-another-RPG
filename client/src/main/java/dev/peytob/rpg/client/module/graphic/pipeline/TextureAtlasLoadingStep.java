package dev.peytob.rpg.client.module.graphic.pipeline;

import dev.peytob.rpg.client.module.graphic.model.Image;
import dev.peytob.rpg.client.module.graphic.service.utils.ImageLoader;
import dev.peytob.rpg.client.module.graphic.service.vendor.TextureService;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.client.module.graphic.model.DefaultTexturesIds.DEFAULT_TEXTURE_ATLAS_ID;
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
        textureService.createTexture(DEFAULT_TEXTURE_ATLAS_ID, GL_RGBA8, testTile);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
