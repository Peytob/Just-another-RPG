package dev.peytob.rpg.client.graphic.service;

import dev.peytob.rpg.client.graphic.model.Image;
import dev.peytob.rpg.client.graphic.resource.Texture;

import java.util.Optional;

public interface TextureService {

    Texture createTexture(String id, Image image);

    boolean removeTexture(Texture texture);

    Optional<Texture> getTextureById(String id);
}
