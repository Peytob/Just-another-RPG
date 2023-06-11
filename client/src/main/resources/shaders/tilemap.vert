#version 330 core

layout (location = 0) in vec2 a_position;


layout (std140) uniform Camera {
    mat4 projection;
};

void main() {
    gl_Position = projection * vec4(a_position, 0.0, 1.0);
}
