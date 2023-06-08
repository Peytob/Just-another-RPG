#version 330 core

layout (location = 0) in vec2 a_tilePosition;
layout (location = 1) in vec2 a_normalizedTextureCoordinates;

layout (std140) uniform Camera {
    mat4 projection;
    ivec2 resolution;
};

out VS_OUT {
    vec2 textureCoordinates;
} vs_out;

void main() {
    gl_Position = projection * vec4(a_tilePosition, 0, 1);
}
