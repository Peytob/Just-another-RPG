package dev.peytob.rpg.client.module.graphic.service.vendor;

import dev.peytob.rpg.client.module.graphic.repository.TextureRepository;
import dev.peytob.rpg.client.module.graphic.resource.Texture;
import dev.peytob.rpg.math.vector.Vec2i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL32.*;

@Component
public class OpenGlTextureService implements TextureService {

    private static final Logger logger = LoggerFactory.getLogger(OpenGlTextureService.class);

    private final TextureRepository textureRepository;

    public OpenGlTextureService(TextureRepository textureRepository) {
        this.textureRepository = textureRepository;
    }

    @Override
    public void createTexture(String textId, Integer format, Integer dataType, Vec2i sizes, ByteBuffer data) {
        logger.info("Creating new texture with id {}", textId);

        int textureId = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexImage2D(GL_TEXTURE_2D, 0, format, sizes.x(), sizes.y(), 0, format, GL_UNSIGNED_BYTE, data);

        Texture texture = new Texture(
            textureId,
            textId,
            format,
            dataType,
            sizes,
            data.asReadOnlyBuffer());

        logger.info("Created texture with id {} ({})", textId, textureId);

        textureRepository.append(texture);
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

        return true;
    }
}
