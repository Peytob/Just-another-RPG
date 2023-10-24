package dev.peytob.rpg.auth.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "User with this username or password not found or deactivated!")
public class LoginFailed extends RuntimeException {
}
