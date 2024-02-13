package dev.peytob.rpg.client.graphic.resource;

import dev.peytob.rpg.math.vector.Vec2i;

public record Texture(
    String id,
    int vendorId,
    Vec2i sizes
) implements GraphicVendorResource {
}