#version 330 core

layout (location = 0) in vec2 a_position;
layout (location = 1) in vec2 a_texturePosition;
layout (location = 2) in int a_textureIndex;

layout (std140) uniform Camera {
    mat4 u_projection;
};

out VS_OUT {
    vec2 texturePosition;
    flat int textureIndex;
} vs_out;

void main() {
    gl_Position = u_projection * vec4(a_position, 0.0, 1.0);
    vs_out.texturePosition = a_texturePosition;
    vs_out.textureIndex = a_textureIndex;
}
