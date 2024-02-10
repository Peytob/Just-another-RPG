package dev.peytob.rpg.client.graphic.resource;

public record VertexArray(
    String id,
    int vendorId
) implements GraphicVendorResource {

    public record VertexArrayAttribute(
        int index,
        int size,
        int type,
        Boolean normalized,
        int stride,
        Long offset
    ) {
    }
}
