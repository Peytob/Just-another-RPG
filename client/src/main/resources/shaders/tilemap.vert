#version 330 core

layout (location = 0) in vec2 a_position;
layout (location = 1) in vec2 a_texturePosition;
//layout (location = 2) in int a_textureIndex;

layout (std140) uniform Camera {
    mat4 projection;
};

//out VS_OUT {
//    vec2 texturePosition;
//} vs_out;

void main() {
    mat4 test = mat4(
    vec4(0.0025,  0.0,    0.0,   0.0),
    vec4(0,       -0.003, 0.0,   0.0),
    vec4(0.0,     0.0,    -1.0,  0.0),
    vec4(-1.0,    1.0,    0.0,   1.0)
    );

    gl_Position = test * vec4(a_position, 0.0, 1.0);
//    gl_Position = test * vec4(a_position, 0.0, 1.0);
//    vs_out.texturePosition = a_texturePosition;
}
