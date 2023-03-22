package dev.peytob.rpg.client.exception.graphic;

public class ShaderProgramLinkException extends RuntimeException {

    private final String infoLog;

    public ShaderProgramLinkException(String infoLog) {
        super("OGL error while linking shader: " + infoLog);
        this.infoLog = infoLog;
    }

    public String getInfoLog() {
        return infoLog;
    }
}
