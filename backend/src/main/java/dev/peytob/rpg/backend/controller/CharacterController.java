package dev.peytob.rpg.backend.controller;

import dev.peytob.rpg.backend.dto.user.CharacterGetDto;
import dev.peytob.rpg.backend.entity.CharacterEntity;
import dev.peytob.rpg.backend.mapper.CharacterMapper;
import dev.peytob.rpg.backend.service.crud.CharacterCrudService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dev.peytob.rpg.backend.entity.RoleStringConstant.ROLE_ADMIN_READER;

@RestController
@RequestMapping("/character")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterCrudService characterCrudService;

    private final CharacterMapper characterMapper;

    @Operation(description = "Get character by id")
    @GetMapping("/{characterId}")
    @Secured(ROLE_ADMIN_READER)
    ResponseEntity<CharacterGetDto> getCharacterById(@PathVariable String characterId) {
        CharacterEntity character = characterCrudService.getById(characterId);
        return ResponseEntity.ok(characterMapper.toCharacterDto(character));
    }
}
