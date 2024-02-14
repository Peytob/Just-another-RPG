package dev.peytob.rpg.client.graphic.service;

import dev.peytob.rpg.client.graphic.resource.UniformBlock;

import java.util.Optional;

public interface UniformBlockService {

    UniformBlock createUniformBlock(String uniformBlockId, int sizesBytes, int bindingIndex);

    boolean removeUniformBlock(UniformBlock uniformBlock);

    Optional<UniformBlock> getUniformBlock(String uniformBlockId);
}
