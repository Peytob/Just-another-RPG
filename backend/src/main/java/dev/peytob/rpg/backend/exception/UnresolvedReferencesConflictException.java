package dev.peytob.rpg.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "References to the entity still exists!")
public class UnresolvedReferencesConflictException extends RuntimeException {

    public UnresolvedReferencesConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
