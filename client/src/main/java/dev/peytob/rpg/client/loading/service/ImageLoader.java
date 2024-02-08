package dev.peytob.rpg.client.loading.service;

import dev.peytob.rpg.client.graphic.model.Image;

import java.io.IOException;
import java.io.InputStream;

public interface ImageLoader {

    Image loadFileImageResource(String filePath);

    Image loadImageResource(InputStream inputStream) throws IOException;
}
