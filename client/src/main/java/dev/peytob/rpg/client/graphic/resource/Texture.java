package dev.peytob.rpg.client.graphic.resource;

public record Texture(
    String id,
    int openGlId
) implements OpenGlResource {
}
