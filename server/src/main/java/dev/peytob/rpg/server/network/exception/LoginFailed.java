package dev.peytob.rpg.server.network.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
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
