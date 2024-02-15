#version 330 core

out vec4 fo_fragmentColor;


in VS_OUT {
    vec2 texturePosition;
    flat int textureIndex;
} vs_out;

void main() {
//    fo_fragmentColor = texture(textureAtlas, vs_in.texturePosition);
    fo_fragmentColor = vec4(vs_out.texturePosition, 0.2f, 1.0f);
}
