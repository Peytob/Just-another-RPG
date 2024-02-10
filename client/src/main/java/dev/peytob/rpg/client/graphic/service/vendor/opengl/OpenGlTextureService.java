package dev.peytob.rpg.client.graphic.service.vendor.opengl;

import dev.peytob.rpg.client.graphic.model.Image;
import dev.peytob.rpg.client.graphic.repository.TextureRepository;
import dev.peytob.rpg.client.graphic.resource.Texture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.lwjgl.opengl.GL33.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenGlTextureService implements dev.peytob.rpg.client.graphic.service.TextureService {

    private final TextureRepository textureRepository;

    @Override
    public Texture createTexture(String id, Image image) {
        log.info("Creating new texture with id {}", id);

        int textureId = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, image.sizes().x(), image.sizes().y(), 0, GL_RGBA, GL_UNSIGNED_BYTE, image.data());
        glGenerateMipmap(GL_TEXTURE_2D);

        Texture texture = new Texture(id, textureId, image.sizes());

        log.info("Created texture with id {} ({})", id, textureId);

        textureRepository.append(texture);

        return texture;
    }

    @Override
    public boolean removeTexture(Texture texture) {
        log.info("Removing texture with id {} ({})", texture.id(), texture.vendorId());

        glDeleteTextures(texture.vendorId());
        return textureRepository.remove(texture);
    }

    @Override
    public Optional<Texture> getTextureById(String id) {
        return textureRepository.getById(id);
    }
}
