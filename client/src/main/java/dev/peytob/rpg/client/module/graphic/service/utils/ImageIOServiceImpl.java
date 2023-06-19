package dev.peytob.rpg.client.module.graphic.service.utils;

import dev.peytob.rpg.client.module.graphic.exception.ImageLoadingException;
import dev.peytob.rpg.client.module.graphic.model.Image;
import dev.peytob.rpg.math.vector.Vec2i;
import dev.peytob.rpg.math.vector.Vectors;
import org.lwjgl.stb.STBImageWrite;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

@Component
public class ImageIOServiceImpl implements ImageIOService {

    /**
     * STB can't load images from classpath.
     */
    @Override
    public Image loaderClasspathImage(String path) {
        try (InputStream imageResource = getClass().getResourceAsStream(path)) {
            if (imageResource == null) {
                throw new ImageLoadingException("Classpath resource with path '" + path + "' not found");
            }

            BufferedImage image = ImageIO.read(imageResource);
            Vec2i sizes = Vectors.immutableVec2i(image.getWidth(), image.getHeight());
            ByteBuffer data = bufferedImageToByteBuffer(image);

            return new Image(sizes, data);
        } catch (IOException e) {
            throw new ImageLoadingException("Classpath image can't be loaded", e);
        }
    }

    @Override
    public void saveImageFile(String filename, Image image) {
        STBImageWrite.nstbi_flip_vertically_on_write(1);
        STBImageWrite.stbi_write_jpg(filename + ".jpg", image.sizes().x(), image.sizes().y(), 4, image.data(), 100);
    }

    private ByteBuffer bufferedImageToByteBuffer(BufferedImage image) {
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        ByteBuffer buffer = ByteBuffer.allocateDirect(image.getWidth() * image.getHeight() * 4);

        for(int h = 0; h < image.getHeight(); h++) {
            for(int w = 0; w < image.getWidth(); w++) {
                int pixel = pixels[h * image.getWidth() + w];

                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }

        buffer.flip();

        return buffer;
    }
}
