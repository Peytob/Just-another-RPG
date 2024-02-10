package dev.peytob.rpg.client.graphic.resource;

import dev.peytob.rpg.client.graphic.model.opengl.ShaderType;

public record Shader(
    String id,
    int vendorId,
    ShaderType shaderType
) implements GraphicVendorResource {
}
