#version 330 core

out vec4 fo_fragmentColor;


in VS_OUT {
    vec2 texturePosition;
    flat int textureIndex;
} vs_out;

uniform sampler2D u_texture;

vec4 getTexel(in vec2 texturePosition, int textureIntex) {
    return texture(u_texture, texturePosition);
}

void main() {
//    fo_fragmentColor = texture(textureAtlas, vs_in.texturePosition);
    fo_fragmentColor = getTexel(vs_out.texturePosition, vs_out.textureIndex);
}
