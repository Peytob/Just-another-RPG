package dev.peytob.rpg.server.network.exception;

public class LoginFailed extends RuntimeException {

    public LoginFailed() {
    }

    public LoginFailed(String message) {
        super(message);
    }

    public LoginFailed(String message, Throwable cause) {
        super(message, cause);
    }
}
