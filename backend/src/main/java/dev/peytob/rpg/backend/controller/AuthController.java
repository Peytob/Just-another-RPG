package dev.peytob.rpg.backend.controller;

import dev.peytob.rpg.backend.dto.auth.LoginDto;
import dev.peytob.rpg.backend.dto.auth.RegistrationDto;
import dev.peytob.rpg.backend.dto.auth.TokenDto;
import dev.peytob.rpg.backend.service.security.AuthProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final AuthProvider userAuthService;

    @Operation(summary = "User login", description = "Generates session token and return it in 'Authorization' header")
    @PostMapping("/login")
    ResponseEntity<Void> login(@RequestBody @Valid LoginDto loginDto) {
        Optional<String> token = userAuthService.loginUser(loginDto.username(), loginDto.password());

        if (token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return ResponseEntity.ok().header(AUTHORIZATION_HEADER, token.get()).build();
        }
    }

    @Operation(summary = "User logout", description = "Deactivates session token from 'Authorization' header")
    @PostMapping("/logout")
    ResponseEntity<Void> logout(@RequestHeader(AUTHORIZATION_HEADER) @Schema(hidden = true) String authorization) {
        userAuthService.logoutUser(authorization);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    ResponseEntity<Void> register(@RequestBody @Valid RegistrationDto registrationDto) {
        String token = userAuthService.registerPlayerUser(registrationDto.username(), registrationDto.password(), registrationDto.email());
        return ResponseEntity.status(HttpStatus.CREATED).header(AUTHORIZATION_HEADER, token).build();
    }

    @PostMapping("/validate")
    ResponseEntity<TokenDto> validate(@RequestHeader(AUTHORIZATION_HEADER) @Schema(hidden = true) String authorization) {
        Optional<TokenDto> token = userAuthService.validateRawToken(authorization);
        if (token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return ResponseEntity.ok().body(token.get());
        }
    }
}
