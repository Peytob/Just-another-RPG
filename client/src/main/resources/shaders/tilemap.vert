#version 330 core

layout (location = 0) in vec2 a_position;
//layout (location = 1) in vec2 a_texturePosition;

//layout (std140) uniform Camera {
//    mat4 projection;
//};
//
//out VS_OUT {
//    vec2 texturePosition;
//} vs_out;

void main() {
    gl_Position = vec4(a_position.x, a_position.y, 0.0, 1.0);
//    vs_out.texturePosition = a_texturePosition;
}
