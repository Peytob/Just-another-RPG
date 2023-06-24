package dev.peytob.rpg.client.module.graphic.service.utils;

import dev.peytob.rpg.client.module.graphic.model.Image;

public interface ImageIOService {

    Image loaderClasspathImage(String path);

    void saveImageFile(String filename, Image image);
}
