package dev.peytob.rpg.client.graphic.service;

import dev.peytob.rpg.client.graphic.repository.TextureRepository;
import dev.peytob.rpg.client.graphic.resource.Texture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TextureService {

    private final TextureRepository textureRepository;

    public Texture createTexture() {
        return null;
    }

    public boolean removeTexture(Texture texture) {
        return false;
    }
}
