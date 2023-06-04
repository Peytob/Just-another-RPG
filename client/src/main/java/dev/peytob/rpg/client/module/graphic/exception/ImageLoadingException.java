package dev.peytob.rpg.client.module.graphic.exception;

public class ImageLoadingException extends RuntimeException {

    public ImageLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageLoadingException(String message) {
        super(message);
    }
}
