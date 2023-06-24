package dev.peytob.rpg.client.module.graphic.state.event;

import dev.peytob.rpg.client.module.graphic.context.component.TilemapTextureAtlasComponent;
import dev.peytob.rpg.client.module.graphic.model.Image;
import dev.peytob.rpg.client.module.graphic.model.TextureAtlas;
import dev.peytob.rpg.client.module.graphic.resource.Texture;
import dev.peytob.rpg.client.module.graphic.service.utils.ImageIOService;
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

    private final ImageIOService imageIOService;

    public CreateMockTextureAtlas(TextureService textureService, ImageIOService imageIOService) {
        this.textureService = textureService;
        this.imageIOService = imageIOService;
    }

    @Override
    public void onStateSetUp(InGameEngineState engineState, EcsContextBuilder contextBuilder) {
        Image testAtlas = imageIOService.loaderClasspathImage("/images/tiles/test_tile.png");
        Texture textureAtlas = textureService.createTexture("tilemap_test_atlas", GL_RGBA8, testAtlas);

        TextureAtlas.TextureAtlasBuilder textureAtlasBuilder = new TextureAtlas.TextureAtlasBuilder(textureAtlas);
        textureAtlasBuilder.appendSprite("blue_tile", rect(0, 0, 0.5f, 0.5f));
        textureAtlasBuilder.appendSprite("red_tile", rect(0.5f, 0, 0.5f, 0.5f));
        textureAtlasBuilder.appendSprite("pink_tile", rect(0, 0.5f, 0.5f, 0.5f));
        textureAtlasBuilder.appendSprite("green_tile", rect(0.5f, 0.5f, 0.5f, 0.5f));
        TextureAtlas builtAtlas = textureAtlasBuilder.build();

        contextBuilder.initializeEntity((entity, ecsContext) ->
            entity.bindComponent(new TilemapTextureAtlasComponent(builtAtlas)));
    }
}
