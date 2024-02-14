package dev.peytob.rpg.client.graphic.model;

import dev.peytob.rpg.math.matrix.Mat4;

public enum DefaultUniformBlocks {
    CAMERA("Camera", Mat4.BYTES, 0);

    private final String name;
    private final int bytes;
    private final int bindingIndex;

    DefaultUniformBlocks(String name, int bytes, int bindingIndex) {
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
