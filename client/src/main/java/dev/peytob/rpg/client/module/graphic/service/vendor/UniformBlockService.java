package dev.peytob.rpg.client.module.graphic.service.vendor;

import dev.peytob.rpg.client.module.graphic.resource.UniformBlock;

public interface UniformBlockService {

    UniformBlock createUniformBlock(String textId, String uniformBlockName, int sizesInBytes, int bindingIndex);

    default UniformBlock createUniformBlock(String uniformBlockName, int sizesInBytes, int bindingIndex) {
        return createUniformBlock(uniformBlockName, uniformBlockName, sizesInBytes, bindingIndex);
    }


    boolean removeUniformBlock(UniformBlock uniformBlock);
}
