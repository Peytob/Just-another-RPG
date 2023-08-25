package dev.peytob.rpg.client.module.base.context.system.loading;

import dev.peytob.rpg.client.context.component.CopyEntityOnChangeStateFlag;
import dev.peytob.rpg.client.fsm.annotation.IncludeInState;
import dev.peytob.rpg.client.fsm.state.instance.InGameLoadingState;
import dev.peytob.rpg.client.module.graphic.context.component.TilemapTextureAtlasComponent;
import dev.peytob.rpg.client.module.graphic.model.Image;
import dev.peytob.rpg.client.module.graphic.model.TextureAtlas;
import dev.peytob.rpg.client.module.graphic.resource.Texture;
import dev.peytob.rpg.client.module.graphic.service.utils.ImageIOService;
import dev.peytob.rpg.client.module.graphic.service.vendor.TextureService;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.system.System;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.math.geometry.Rectangles.rect;
import static org.lwjgl.opengl.GL11.GL_RGBA8;

@Component
@IncludeInState(state = InGameLoadingState.class, order = 1)
public class TilemapAtlasBuildingSystem implements System {

    private static final Logger log = LoggerFactory.getLogger(TilemapAtlasBuildingSystem.class);

    private final ImageIOService imageIOService;

    private final TextureService textureService;

    public TilemapAtlasBuildingSystem(ImageIOService imageIOService, TextureService textureService) {
        this.imageIOService = imageIOService;
        this.textureService = textureService;
    }

    @Override
    public void execute(EcsContext context) {
        // TODO Make it async (but createTexture works only from main thread!)

        final String tilemapEntityId = "tilemap_texture_atlas";

        if (context.getEntityById(tilemapEntityId).isPresent()) {
            return;
        }

        TextureAtlas textureAtlas = buildStaticTextureAtlas();

        Entity tilemapTextureAtlas = context.createEntity("tilemap_texture_atlas");
        tilemapTextureAtlas.bindComponent(new TilemapTextureAtlasComponent(textureAtlas));
        tilemapTextureAtlas.bindComponent(new CopyEntityOnChangeStateFlag());
    }

    private TextureAtlas buildStaticTextureAtlas() {
        log.info("Loading tilemap texture atlas data");

        Image testAtlas = imageIOService.loaderClasspathImage("/assets/sprites/tiles/tilemap.png");
        Texture textureAtlas = textureService.createTexture("tilemap_texture_atlas", GL_RGBA8, testAtlas);

        TextureAtlas.TextureAtlasBuilder textureAtlasBuilder = new TextureAtlas.TextureAtlasBuilder(textureAtlas);
        textureAtlasBuilder.appendSprite("blue_tile", rect(0, 0, 0.5f, 0.5f));
        textureAtlasBuilder.appendSprite("red_tile", rect(0.5f, 0, 0.5f, 0.5f));
        textureAtlasBuilder.appendSprite("pink_tile", rect(0, 0.5f, 0.5f, 0.5f));
        textureAtlasBuilder.appendSprite("green_tile", rect(0.5f, 0.5f, 0.5f, 0.5f));
        return textureAtlasBuilder.build();
    }
}
