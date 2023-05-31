#version 330 core

layout (location = 0) in uint a_tileId;

uniform ivec2 u_mapSizeTiles;
uniform ivec2 u_tileSizesPixels;

layout (std140) uniform Camera {
    mat4 projection;
    ivec2 resolution;
};

out VS_OUT {
    uint tileId;
} vs_out;

void main() {
    int index = gl_VertexID;

    vec2 tileCoordinates = vec2(
        index / u_mapSizeTiles.y * u_tileSizesPixels.x,
        index % u_mapSizeTiles.y * u_tileSizesPixels.y
    );

    gl_Position = vec4(tileCoordinates, 0, 1);

    vs_out.tileId = a_tileId;
}
