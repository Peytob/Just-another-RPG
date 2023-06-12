#version 330 core

uniform sampler2D textureAtlas;

in VS_OUT {
    vec2 texturePosition;
} vs_in;

out vec4 fo_fragmentColor;

void main() {
    fo_fragmentColor = texture(textureAtlas, vs_in.texturePosition);
}
