package dev.peytob.rpg.client.module.graphic.exception;

public class ShaderCompilationException extends RuntimeException {

    private final String infoLog;

    public ShaderCompilationException(String infoLog) {
        super("OGL error while compiling shader: " + infoLog);
        this.infoLog = infoLog;
    }

    public String getInfoLog() {
        return infoLog;
    }
}