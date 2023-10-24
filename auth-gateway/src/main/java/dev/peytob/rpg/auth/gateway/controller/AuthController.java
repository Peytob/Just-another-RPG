package dev.peytob.rpg.auth.gateway.controller;

import dev.peytob.rpg.auth.gateway.dto.LoginDto;
import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.service.LoginService;
import dev.peytob.rpg.auth.gateway.service.RealmCrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{realmName}/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final LoginService loginService;

    private final RealmCrudService realmCrudService;

    @Operation(summary = "User login", description = "Generates session token and return it in 'Authorization' header")
    @PostMapping("/login")
    ResponseEntity<Void> login(@PathVariable @NotEmpty String realmName, @RequestBody @Valid LoginDto loginDto) {
        log.info("User with username {} is trying to login in realm with name {}", loginDto.username(), realmName);
        Realm realm = realmCrudService.getRealmByName(realmName);
        String token = loginService.loginUser(loginDto.username(), loginDto.password(), loginDto.tokenType(), realm);
        return ResponseEntity.noContent().header(AUTHORIZATION_HEADER, token).build();
    }

    @Operation(summary = "User logout", description = "Deactivates session token from 'Authorization' header")
    @PostMapping("/logout")
    void logout(@PathVariable @NotEmpty String realmName) {
    }

    @Operation(summary = "Validation for token", description = "Validates session token from 'Authorization' header. Returns auth details.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Token is valid"),
        @ApiResponse(responseCode = "403", description = "Token is invalid or expired")
    })
    @PostMapping("/validate")
    ResponseEntity<Void> validate(@PathVariable @NotEmpty String realmName) {
        return ResponseEntity.ok().build();
    }
}
