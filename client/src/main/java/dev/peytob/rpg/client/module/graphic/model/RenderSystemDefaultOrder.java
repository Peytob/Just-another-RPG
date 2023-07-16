package dev.peytob.rpg.client.module.graphic.model;

import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.RENDERING;

public enum RenderSystemDefaultOrder {;

    public static final int BEFORE_MAIN_RENDERING = RENDERING;

    public static final int MAIN_RENDERING = BEFORE_MAIN_RENDERING + 256;

    public static final int AFTER_MAIN_RENDERING = MAIN_RENDERING + 512;
}
