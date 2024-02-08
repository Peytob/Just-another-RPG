package dev.peytob.rpg.client.graphic.ecs.component;

import dev.peytob.rpg.client.graphic.model.RenderingContext;
import dev.peytob.rpg.ecs.component.SingletonComponent;

public class RenderContextComponent implements SingletonComponent {

    private RenderingContext renderingContext;

    public RenderContextComponent(RenderingContext renderingContext) {
        this.renderingContext = renderingContext;
    }

    public RenderingContext getRenderContext() {
        return renderingContext;
    }
}
