package dev.peytob.rpg.client.module.graphic.service.vendor;

import dev.peytob.rpg.client.module.graphic.model.Image;
import dev.peytob.rpg.math.geometry.RectI;
import dev.peytob.rpg.math.vector.Vec2i;
import org.lwjgl.BufferUtils;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glReadPixels;

@Component
public class OpenGlScreenshotService implements ScreenshotService {

    @Override
    public Image makeScreenshot(RectI area) {
        Vec2i imageSize = area.size();
        // 4 bytes for RGBA
        ByteBuffer imageData = BufferUtils.createByteBuffer(imageSize.x() * imageSize.y() * 4);
        glReadPixels(area.topLeft().x(), area.topLeft().y(), imageSize.x(), imageSize.y(), GL_RGBA, GL_UNSIGNED_BYTE, imageData);
        return new Image(imageSize, imageData);
    }
}
