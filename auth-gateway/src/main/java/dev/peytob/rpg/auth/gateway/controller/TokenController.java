package dev.peytob.rpg.auth.gateway.controller;

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
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static dev.peytob.rpg.auth.gateway.configuration.SecurityConfiguration.AUTHORIZATION_HEADER;

@RestController
@RequestMapping("/realm/{realmId}/token")
@RequiredArgsConstructor
@Slf4j
public class TokenController {

    private final RealmCrudService realmCrudService;

    private final LoginService loginService;

    private final TokenMapper tokenMapper;

    @Operation(summary = "Validation for token", description = "Secured mirror of '/auth/validate'")
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
