package dev.peytob.rpg.client.module.graphic.service.vendor;

import dev.peytob.rpg.client.module.graphic.repository.TextureAtlasRepository;
import dev.peytob.rpg.client.module.graphic.repository.TextureRepository;
import dev.peytob.rpg.client.module.graphic.resource.Texture;
import dev.peytob.rpg.client.module.graphic.resource.TextureAtlas;
import dev.peytob.rpg.math.vector.Vec2i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL32.*;

@Component
public class OpenGlTextureService implements TextureService {

    private static final Logger logger = LoggerFactory.getLogger(OpenGlTextureService.class);

    private final TextureRepository textureRepository;

    private final TextureAtlasRepository textureAtlasRepository;

    public OpenGlTextureService(TextureRepository textureRepository, TextureAtlasRepository textureAtlasRepository) {
        this.textureRepository = textureRepository;
        this.textureAtlasRepository = textureAtlasRepository;
    }

    @Override
    public Texture createTexture(String textId, Integer format, Vec2i sizes, ByteBuffer data) {
        logger.info("Creating new texture with id {}", textId);

        int textureId = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexImage2D(GL_TEXTURE_2D, 0, format, sizes.x(), sizes.y(), 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
        glGenerateMipmap(GL_TEXTURE_2D);

        Texture texture = new Texture(
            textureId,
            textId,
            format,
            sizes,
            data.asReadOnlyBuffer());

        logger.info("Created texture with id {} ({})", textId, textureId);

        textureRepository.append(texture);

        return texture;
    }

    @Override
    public boolean removeTexture(Texture texture) {
        logger.info("Removing texture with id {} ({})", texture.textId(), texture.id());

        if (textureRepository.contains(texture.id())) {
            logger.warn("texture with id {} ({}) not found while removing", texture.textId(), texture.id());
            return false;
        }

        glDeleteTextures(texture.id());

        Texture textureFromRepository = textureRepository.getById(texture.id());
        textureRepository.remove(textureFromRepository);

        Collection<TextureAtlas> atlases = textureAtlasRepository.getAtlasesByTexture(texture);
        if (!atlases.isEmpty()) {
            logger.info("Removing all atlases linked with texture {} ({})", texture.textId(), texture.id());
            removeAllAtlases(List.copyOf(atlases));
        }

        return true;
    }

    @Override
    public TextureAtlas wrapTextureAsAtlas(String textId, Texture texture, Map<String, TextureAtlas.Sprite> sprites) {
        logger.info("Wrapping texture {} ({}) as texture atlas with id {}", texture.textId(), texture.id(), textId);

        TextureAtlas textureAtlas = new TextureAtlas(texture.id(), textId, sprites, texture);
        textureAtlasRepository.append(textureAtlas);
        return textureAtlas;
    }

    private void removeAllAtlases(Collection<TextureAtlas> atlases) {
        atlases.forEach(this::removeAtlas);
    }

    private boolean removeAtlas(TextureAtlas textureAtlas) {
        logger.info("Removing texture atlas with id {} ({})", textureAtlas.textId(), textureAtlas.id());
        return textureAtlasRepository.remove(textureAtlas);
    }
}
