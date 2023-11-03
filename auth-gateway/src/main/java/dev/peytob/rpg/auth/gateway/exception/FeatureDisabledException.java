package dev.peytob.rpg.auth.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
public class FeatureDisabledException extends RuntimeException {
}
