package dev.peytob.rpg.server.network.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.peytob.rpg.core.network.model.client.ClientEvent;
import dev.peytob.rpg.core.network.model.server.WorldState;
import dev.peytob.rpg.server.gameplay.repository.CharacterSessionRepository;
import dev.peytob.rpg.server.gameplay.resource.Character;
import dev.peytob.rpg.server.gameplay.resource.CharacterSession;
import dev.peytob.rpg.server.gameplay.service.context.CharacterContextInteractionService;
import dev.peytob.rpg.server.gameplay.service.context.WorldContextRunner;
import dev.peytob.rpg.server.gameplay.service.context.WorldContextService;
import dev.peytob.rpg.server.gameplay.service.data.CharacterService;
import dev.peytob.rpg.server.network.dto.TokenDto;
import dev.peytob.rpg.server.network.service.AccountAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

import static dev.peytob.rpg.server.network.utils.DefaultHeaders.CHARACTER_ID_HEADER;

// TODO Decomposition
@Component
@RequiredArgsConstructor
@Slf4j
public class ContextEventsWebSocketHandler extends BaseWebSocketHandler<Collection<ClientEvent<?>>> {

    private static final String USERNAME_ATTRIBUTE = "username";
    private static final String USER_ID_ATTRIBUTE = "userId";
    private static final String TOKEN_ATTRIBUTE = "token";

    private final AccountAuthService accountAuthService;

    private final CharacterContextInteractionService characterContextInteractionService;

    private final WorldContextService worldContextService;

    private final CharacterSessionRepository characterSessionRepository;

    private final CharacterService characterService;

    private final ObjectMapper objectMapper;

    @Override
    public String getPath() {
        return "/context/events";
    }

    @Override
    protected void handleDeserializedMessage(WebSocketSession session, Collection<ClientEvent<?>> message) {
        String userId = session.getAttributes().get(USER_ID_ATTRIBUTE).toString();

        CharacterSession userCharacterSession = characterSessionRepository.getUserCharacterSession(userId);
        String worldContextRunnerName = userCharacterSession.getWorldContextRunnerName();

        WorldContextRunner worldContextRunner = worldContextService.getWorldContextRunner(worldContextRunnerName);

        worldContextRunner.executeBeforeFrame(context -> message.stream()
            .map(ClientEvent::data)
            .forEach(context::addEvent));

        worldContextRunner.executeBeforeFrame(context -> {
            try {
                WorldState worldState = characterContextInteractionService.getAwailableWorldState(userCharacterSession.getCharacter());
                String worldStateJson = objectMapper.writeValueAsString(worldState);
                session.sendMessage(new TextMessage(worldStateJson));
            } catch (IOException e) {
                log.error("Exception while serializing world state", e);
            }
        });
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Principal principal = session.getPrincipal();

        if (principal == null) {
            log.error("Websocket authorization failed: principal not found");
            session.close(CloseStatus.GOING_AWAY);
            return;
        }

        String token = principal.getName();

        Optional<TokenDto> tokenDetailsOptional = accountAuthService.validate(token);

        if (tokenDetailsOptional.isEmpty()) {
            log.error("Websocket authorization failed: token is invalid");
            session.close(CloseStatus.GOING_AWAY);
            return;
        }

        if (!session.getHandshakeHeaders().containsKey(CHARACTER_ID_HEADER)) {
            log.error("Websocket handshake headers is invalid: character id not found");
        }

        TokenDto tokenDetails = tokenDetailsOptional.get();

        session.getAttributes().put(USERNAME_ATTRIBUTE, tokenDetails.username());
        session.getAttributes().put(USER_ID_ATTRIBUTE, tokenDetails.userId());
        session.getAttributes().put(TOKEN_ATTRIBUTE, token);

        if (characterSessionRepository.containsUserCharacterSession(tokenDetails.userId())) {
            log.error("Websocket authorization failed: user character session not found");
            session.close(CloseStatus.GOING_AWAY);
            return;
        }

        log.info("Established connection {} with user {} ({})", session.getId(), tokenDetails.username(), session.getRemoteAddress());

        String characterId = session.getHandshakeHeaders().getFirst(CHARACTER_ID_HEADER);
        Character character = characterService.getUserCharacterById(tokenDetails.userId(), characterId);

        characterContextInteractionService.enterToContext(character);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        if (!session.getAttributes().containsKey(USER_ID_ATTRIBUTE)) {
            log.warn("Connection {} closed with status {}. User data not found in session attributes.", session.getId(), status);
            return;
        }

        String username = session.getAttributes().get(USERNAME_ATTRIBUTE).toString();

        log.info("Connection {} with user {} closed with status {}", session.getId(), username, status);

        String userId = session.getAttributes().get(USER_ID_ATTRIBUTE).toString();

        CharacterSession userCharacterSession = characterSessionRepository.getUserCharacterSession(userId);
        characterContextInteractionService.exitFromContext(userCharacterSession.getCharacter());
    }
}
