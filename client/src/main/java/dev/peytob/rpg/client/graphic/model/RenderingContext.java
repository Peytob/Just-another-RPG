package dev.peytob.rpg.client.graphic.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RenderingContext {

    private Camera camera;
}
