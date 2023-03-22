package dev.peytob.rpg.engine.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public enum FileUtils {;

    public static String readClasspathFile(ClassLoader classLoader, String path) throws IOException {
        try (InputStream inputStream = classLoader.getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new FileNotFoundException("Classpath resource " + path + " not found!");
            }

            BufferedInputStream bis = new BufferedInputStream(inputStream);
            ByteArrayOutputStream buf = new ByteArrayOutputStream();

            for (int result = bis.read(); result != -1; result = bis.read()) {
                buf.write((byte) result);
            }

            return buf.toString(StandardCharsets.UTF_8.name());
        }
    }
}
