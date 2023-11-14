package dev.peytob.rpg.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "User with this username and password not found or deactivated!")
public class LoginFailed extends RuntimeException {
}

