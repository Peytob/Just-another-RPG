package dev.peytob.rpg.auth.gateway.controller;

import dev.peytob.rpg.auth.gateway.dto.auth.LoginDto;
import dev.peytob.rpg.auth.gateway.dto.auth.RegistrationDto;
import dev.peytob.rpg.auth.gateway.dto.auth.TokenInfoDto;
import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.entity.Token;
import dev.peytob.rpg.auth.gateway.mapper.TokenMapper;
import dev.peytob.rpg.auth.gateway.service.LoginService;
import dev.peytob.rpg.auth.gateway.service.RealmCrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static dev.peytob.rpg.auth.gateway.configuration.SecurityConfiguration.AUTHORIZATION_HEADER;

@RestController
@RequestMapping("/{realmName}/auth")
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(name = "auth.public-endpoints-enabled")
public class AuthController {

    private final LoginService loginService;

    private final RealmCrudService realmCrudService;

    private final TokenMapper tokenMapper;

    @Operation(summary = "User login", description = "Generates session token and return it in 'Authorization' header")
    @PostMapping("/login")
    ResponseEntity<Void> login(@PathVariable @NotEmpty String realmName, @RequestBody @Valid LoginDto loginDto) {
        log.info("User with username '{}' is trying to login in realm with name '{}'", loginDto.username(), realmName);
        Realm realm = realmCrudService.getRealmByName(realmName);
        String token = loginService.loginUser(loginDto.username(), loginDto.password(), realm);
        return ResponseEntity.noContent().header(AUTHORIZATION_HEADER, token).build();
    }

    @Operation(summary = "User logout", description = "Deactivates session token from 'Authorization' header")
    @PostMapping("/logout")
    ResponseEntity<Void> logout(@PathVariable @NotEmpty String realmName, @RequestHeader(AUTHORIZATION_HEADER) String authorization) {
        log.info("Deactivating token **** in realm '{}' by user", realmName);
        Realm realm = realmCrudService.getRealmByName(realmName);
        loginService.logout(authorization, realm);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "User logout", description = "Creates new user and returns new session token from 'Authorization' header")
    @PostMapping("/register")
    ResponseEntity<Void> register(@PathVariable @NotEmpty String realmName, @RequestBody @Valid RegistrationDto registrationDto) {
        log.info("Registering new user in realm '{}' by user request", realmName);
        Realm realm = realmCrudService.getRealmByName(realmName);
        String token = loginService.registerUser(registrationDto.username(), registrationDto.password(), registrationDto.email(), realm);
        return ResponseEntity.status(HttpStatus.CREATED).header(AUTHORIZATION_HEADER, token).build();
    }

    @Operation(summary = "Validation for token", description = "Validates session token from 'Authorization' header. Returns auth details.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Token is valid", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TokenInfoDto.class))),
        @ApiResponse(responseCode = "403", description = "Token is invalid or expired")
    })
    @PostMapping("/validate")
    ResponseEntity<?> validate(@PathVariable @NotEmpty String realmName, @RequestHeader(AUTHORIZATION_HEADER) String authorization) {
        Realm realm = realmCrudService.getRealmByName(realmName);
        Optional<Token> token = loginService.validateToken(authorization, realm);
        if (token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            TokenInfoDto tokenDto = tokenMapper.toTokenInfoDto(token.get(), realm);
            return ResponseEntity.ok().body(tokenDto);
        }
    }
}
