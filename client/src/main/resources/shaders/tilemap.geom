#version 330 core

layout (points) in;
layout (triangle_strip, max_vertices = 4) out;

out vec2 textureCoordinates;

layout (std140) uniform Camera {
    mat4 projection;
    ivec2 resolution;
};

uniform ivec2 u_tileSizesPixels;

in VS_OUT {
    uint tileId;
} gs_in[];

void main() {
    gl_Position = projection * gl_in[0].gl_Position;
    textureCoordinates = vec2(0.0, 0.0);
    EmitVertex();

    gl_Position = projection * (gl_in[0].gl_Position + vec4(u_tileSizesPixels.x, 0.0, 0.0, 0.0));
    textureCoordinates = vec2(1.0, 0.0);
    EmitVertex();

    gl_Position = projection * (gl_in[0].gl_Position + vec4(0.0, u_tileSizesPixels.y, 0.0, 0.0));
    textureCoordinates = vec2(0.0, 1.0);
    EmitVertex();

    gl_Position = projection * (gl_in[0].gl_Position + vec4(u_tileSizesPixels.x, u_tileSizesPixels.y, 0.0, 0.0));
    textureCoordinates = vec2(1.0, 1.0);
    EmitVertex();

    EndPrimitive();
}
