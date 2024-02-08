package dev.peytob.rpg.client.loading.service;

import dev.peytob.rpg.client.graphic.exception.ImageLoadingException;
import dev.peytob.rpg.client.graphic.model.Image;
import dev.peytob.rpg.math.vector.Vec2i;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

import static dev.peytob.rpg.math.vector.Vectors.immutableVec2i;

@Service
@Slf4j
public class ImageIoImageLoader implements ImageLoader {
    @Override
    public Image loadFileImageResource(String filePath) {
        log.info("Loading image from file: {}", filePath);

        try (InputStream inputStream = Files.newInputStream(Path.of(filePath))) {
            return loadImageResource(inputStream);
        } catch (IOException e) {
            throw new ImageLoadingException("Image " + filePath + " cant be loaded: ", e);
        }
    }

    @Override
    public Image loadImageResource(InputStream inputStream) throws IOException {
        log.info("Parsing input stream with image");

        BufferedImage image = ImageIO.read(inputStream);
        Vec2i sizes = immutableVec2i(image.getWidth(), image.getHeight());
        ByteBuffer data = bufferedImageToByteBuffer(image);
        return new Image(sizes, data);
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
