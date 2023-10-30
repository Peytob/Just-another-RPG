package dev.peytob.rpg.auth.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnresolvedReferencesConflictException extends RuntimeException {

    public UnresolvedReferencesConflictException() {
        this("References to the entity still exists!");
    }

    public UnresolvedReferencesConflictException(String message) {
        super(message);
    }
}
