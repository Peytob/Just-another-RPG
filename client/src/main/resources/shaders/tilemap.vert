#version 330 core

layout (location = 0) in uint a_tileId;

//uniform ivec2 u_mapSizeTiles;

layout (std140) uniform Camera {
    mat4 projection;
};

out VS_OUT {
    uint tileId;
} vs_out;

void main() {
    int index = gl_VertexID;
    ivec2 u_mapSizeTiles = ivec2(16, 16);

    vec2 tileCoordinates = vec2(
        index / u_mapSizeTiles.y,
        index % u_mapSizeTiles.y
    );

    gl_Position = projection * vec4(tileCoordinates, 0, 1);
    vs_out.tileId = a_tileId;
}
