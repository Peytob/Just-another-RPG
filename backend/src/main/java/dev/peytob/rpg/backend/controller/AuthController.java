package dev.peytob.rpg.backend.controller;

import dev.peytob.rpg.backend.dto.auth.LoginDto;
import dev.peytob.rpg.backend.dto.auth.RegistrationDto;
import dev.peytob.rpg.backend.dto.auth.TokenDto;
import dev.peytob.rpg.backend.service.UserAuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final UserAuthService userAuthService;

    @Operation(summary = "User login", description = "Generates session token and return it in 'Authorization' header")
    @RequestMapping("/login")
    ResponseEntity<Void> login(@RequestBody @Valid LoginDto loginDto) {
        Optional<String> token = userAuthService.loginUser(loginDto.username(), loginDto.password());

        if (token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return ResponseEntity.ok().header(AUTHORIZATION_HEADER, token.get()).build();
        }
    }

    @RequestMapping("/logout")
    ResponseEntity<Void> logout(@RequestHeader(AUTHORIZATION_HEADER) String authorization) {
        userAuthService.logoutUser(authorization);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping("/register")
    ResponseEntity<Void> register(@RequestBody @Valid RegistrationDto registrationDto) {
        String token = userAuthService.registerUser(registrationDto.username(), registrationDto.password(), registrationDto.email());
        return ResponseEntity.status(HttpStatus.CREATED).header(AUTHORIZATION_HEADER, token).build();
    }

    @RequestMapping("/validate")
    ResponseEntity<TokenDto> validate(@RequestHeader(AUTHORIZATION_HEADER) String authorization) {
        Optional<TokenDto> token = userAuthService.validateToken(authorization);
        if (token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return ResponseEntity.ok().body(token.get());
        }
    }
}
