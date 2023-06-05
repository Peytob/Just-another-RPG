#version 330 core

out vec4 fo_fragmentColor;
in vec2 textureCoordinates;

uniform sampler2D textureAtlas;

void main() {
    fo_fragmentColor = texture(textureAtlas, textureCoordinates);
}
