#version 330 core

out vec4 fo_fragmentColor;

void main() {
//    fo_fragmentColor = texture(textureAtlas, vs_in.texturePosition);
    fo_fragmentColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);
}
