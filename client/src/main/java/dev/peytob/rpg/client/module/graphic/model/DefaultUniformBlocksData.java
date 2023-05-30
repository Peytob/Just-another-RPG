package dev.peytob.rpg.client.module.graphic.model;

import dev.peytob.rpg.math.matrix.Mat4;
import dev.peytob.rpg.math.vector.Vec2i;

public enum DefaultUniformBlocksData {
    CAMERA("Camera", Mat4.BYTES + Vec2i.BYTES, 0);

    private final String name;
    private final int bytes;
    private final int bindingIndex;

    DefaultUniformBlocksData(String name, int bytes, int bindingIndex) {
        this.name = name;
        this.bytes = bytes;
        this.bindingIndex = bindingIndex;
    }

    public String getName() {
        return name;
    }

    public int getBindingIndex() {
        return bindingIndex;
    }

    public int getSizes() {
        return this.bytes;
    }
}
