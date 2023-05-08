package dev.peytob.rpg.client.module.graphic.resource;

import dev.peytob.rpg.engine.resource.Resource;

import java.util.Map;

public record ShaderProgram(
        Integer id,
        String textId,
        Map<String, ShaderProgramUniform> uniformLocations
) implements Resource {

    public record ShaderProgramUniform(
            String name,
            Integer location,
            Integer size,
            Integer type
    ) {
    }
}
