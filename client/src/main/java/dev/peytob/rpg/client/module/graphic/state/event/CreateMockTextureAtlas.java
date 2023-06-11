package dev.peytob.rpg.client.module.graphic.state.event;

import dev.peytob.rpg.client.module.graphic.context.component.TextureAtlasComponent;
import dev.peytob.rpg.client.module.graphic.model.Image;
import dev.peytob.rpg.client.module.graphic.model.TextureAtlas;
import dev.peytob.rpg.client.module.graphic.resource.Texture;
import dev.peytob.rpg.client.module.graphic.service.utils.ImageLoader;
import dev.peytob.rpg.client.module.graphic.service.vendor.TextureService;
import dev.peytob.rpg.client.state.InGameEngineState;
import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.engine.state.event.StateSetUpEventHandler;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.math.geometry.Rectangles.rect;
import static org.lwjgl.opengl.GL11.GL_RGBA8;

@Component
public class CreateMockTextureAtlas extends StateSetUpEventHandler<InGameEngineState> {

    private final TextureService textureService;

    private final ImageLoader imageLoader;

    public CreateMockTextureAtlas(TextureService textureService, ImageLoader imageLoader) {
        this.textureService = textureService;
        this.imageLoader = imageLoader;
    }

    @Override
    public void onStateSetUp(InGameEngineState engineState, EcsContextBuilder contextBuilder) {
        Image testAtlas = imageLoader.loaderClasspathImage("/images/tiles/test_tile.png");
        Texture textureAtlas = textureService.createTexture("tilemap_test_atlas", GL_RGBA8, testAtlas);

        TextureAtlas.TextureAtlasBuilder textureAtlasBuilder = new TextureAtlas.TextureAtlasBuilder(textureAtlas);
        textureAtlasBuilder.appendSprite("tile1", rect(0, 0, 0.5f, 0.5f));
        textureAtlasBuilder.appendSprite("tile2", rect(0.5f, 0, 0.5f, 0.5f));
        textureAtlasBuilder.appendSprite("tile3", rect(0, 0.5f, 0.5f, 0.5f));
        textureAtlasBuilder.appendSprite("tile4", rect(0.5f, 0.5f, 0.5f, 0.5f));
        TextureAtlas builtAtlas = textureAtlasBuilder.build();

        contextBuilder.initializeEntity((entity, ecsContext) ->
            entity.bindComponent(new TextureAtlasComponent(builtAtlas)));
    }
}
