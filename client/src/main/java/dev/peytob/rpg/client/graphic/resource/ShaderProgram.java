package dev.peytob.rpg.client.graphic.resource;

import java.util.Map;

public record ShaderProgram(
    String id,
    int vendorId,
    Map<String, ShaderProgramUniform> uniformLocations
) implements GraphicVendorResource {

    public record ShaderProgramUniform(
        String name,
        int location,
        int size,
        int type
    ) {
    }
}
