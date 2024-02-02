package dev.peytob.rpg.server.network.controller;

import dev.peytob.rpg.server.network.dto.TokenDto;
import dev.peytob.rpg.server.network.model.LoginDto;
import dev.peytob.rpg.server.network.service.AccountAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import static dev.peytob.rpg.server.network.utils.DefaultHeaders.AUTHORIZATION_HEADER;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AccountAuthService accountAuthService;

    @PostMapping("/login")
    ResponseEntity<Void> login(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        log.info("Login user with username '{}'. Remote address: {}. ", loginDto.username(), request.getRemoteAddr());
        String token = accountAuthService.login(loginDto.username(), loginDto.password());
        return ResponseEntity.ok().header(AUTHORIZATION_HEADER, token).build();
    }

    @PostMapping("/logout")
    ResponseEntity<Void> logout(@RequestHeader(AUTHORIZATION_HEADER) String authorization) {
        accountAuthService.logout(authorization);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/validate")
    ResponseEntity<TokenDto> validate(@RequestHeader(AUTHORIZATION_HEADER) String authorization) {
        Optional<TokenDto> token = accountAuthService.validate(authorization);
        if (token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return ResponseEntity.ok().body(token.get());
        }
    }
}
