package dev.peytob.rpg.client.module.graphic.service.vendor;

import dev.peytob.rpg.client.module.graphic.resource.UniformBuffer;

public interface UniformBufferService {

    UniformBuffer createUniformBlock(String textId, int sizesInBytes);

    boolean removeUniformBlock(UniformBuffer uniformBuffer);
}
