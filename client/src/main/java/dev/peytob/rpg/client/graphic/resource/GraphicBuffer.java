package dev.peytob.rpg.client.graphic.resource;

import dev.peytob.rpg.client.graphic.model.opengl.BufferTarget;

public record GraphicBuffer(
    String id,
    int vendorId,
    BufferTarget target
) implements GraphicVendorResource {
}
