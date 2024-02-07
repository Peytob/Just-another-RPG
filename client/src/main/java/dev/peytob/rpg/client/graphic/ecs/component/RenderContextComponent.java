package dev.peytob.rpg.client.graphic.ecs.component;

import dev.peytob.rpg.client.graphic.model.RenderContext;
import dev.peytob.rpg.ecs.component.SingletonComponent;

public class RenderContextComponent implements SingletonComponent {

    private RenderContext renderContext;

    public RenderContextComponent(RenderContext renderContext) {
        this.renderContext = renderContext;
    }

    public RenderContext getRenderContext() {
        return renderContext;
    }
}
