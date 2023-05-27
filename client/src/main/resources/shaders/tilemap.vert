#version 330 core

layout (location = 0) in uint a_tileId;

/**
 * Sizes of map in tiles.
**/
uniform ivec2 mapSize;

out VS_OUT {
    uint tileId;
} vs_out;

void main() {
    int index = gl_VertexID;

    vec2 tileCoordinates = vec2(
        index / mapSize.y,
        index % mapSize.y
    );

    gl_Position = vec4(tileCoordinates, 0, 1);
    vs_out.tileId = a_tileId;
}
