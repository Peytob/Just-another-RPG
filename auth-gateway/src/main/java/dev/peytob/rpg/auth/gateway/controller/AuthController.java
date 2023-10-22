package dev.peytob.rpg.auth.gateway.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{realm}/auth")
@RequiredArgsConstructor
public class AuthController {

    @Operation(summary = "User login", description = "Generates session token and return it in 'Authorization' header")
    @PostMapping("/login")
    void login() {
    }

    @Operation(summary = "User logout", description = "Deactivates session token from 'Authorization' header")
    @PostMapping("/logout")
    void logout() {
    }

    @Operation(summary = "Validation for token", description = "Validates session token from 'Authorization' header. Returns auth details.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Token is valid"),
        @ApiResponse(responseCode = "403", description = "Token is invalid or expired")
    })
    @PostMapping("/validate")
    ResponseEntity<Void> validate() {
        return ResponseEntity.ok().build();
    }
}
