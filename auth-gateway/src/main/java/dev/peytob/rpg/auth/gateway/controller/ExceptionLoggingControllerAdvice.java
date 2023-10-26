package dev.peytob.rpg.auth.gateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionLoggingControllerAdvice {

    @ExceptionHandler(Exception.class)
    void logException(Exception e, HttpServletRequest httpServletRequest) throws Exception {
        String requestURI = httpServletRequest.getRequestURI();
        String requestMethod = httpServletRequest.getMethod();
        log.error("Unhandled exception on {} '{}' request", requestMethod, requestURI, e);
        throw e;
    }
}
