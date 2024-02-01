package dev.peytob.rpg.server.network.websocket.handler;

import dev.peytob.rpg.core.network.model.client.ClientEvent;
import dev.peytob.rpg.server.gameplay.repository.CharacterSessionRepository;
import dev.peytob.rpg.server.gameplay.resource.CharacterSession;
import dev.peytob.rpg.server.gameplay.service.context.WorldContextRunner;
import dev.peytob.rpg.server.gameplay.service.context.WorldContextService;
import dev.peytob.rpg.server.network.dto.TokenDto;
import dev.peytob.rpg.server.network.service.AccountAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ContextEventsWebSocketHandler extends BaseWebSocketHandler<Collection<ClientEvent<?>>> {

    private static final String USERNAME_ATTRIBUTE = "username";
    private static final String USER_ID_ATTRIBUTE = "userId";
    private static final String TOKEN_ATTRIBUTE = "token";

    private final AccountAuthService accountAuthService;

    private final WorldContextService worldContextService;

    private final CharacterSessionRepository characterSessionRepository;

    @Override
    public String getPath() {
        return "/context/events";
    }

    @Override
    protected void handleDeserializedMessage(WebSocketSession session, Collection<ClientEvent<?>> message) {
        if (message.isEmpty()) {
            return;
        }

        String userId = session.getAttributes().get(USER_ID_ATTRIBUTE).toString();

        CharacterSession userCharacterSession = characterSessionRepository.getUserCharacterSession(userId);
        String worldContextRunnerId = userCharacterSession.getWorldContextRunnerId();

        WorldContextRunner worldContextRunner = worldContextService.getWorldContextRunner(worldContextRunnerId);

        worldContextRunner.executeBeforeFrame(context -> message.stream()
            .map(ClientEvent::data)
            .forEach(context::addEvent));
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

        TokenDto tokenDetails = tokenDetailsOptional.get();

        if (characterSessionRepository.containsUserCharacterSession(tokenDetails.userId())) {
            log.error("Websocket authorization failed: user character session not found");
            session.close(CloseStatus.GOING_AWAY);
            return;
        }

        log.info("Established connection {} with user {} ({})", session.getId(), tokenDetails.username(), session.getRemoteAddress());

        session.getAttributes().put(USERNAME_ATTRIBUTE, tokenDetails.username());
        session.getAttributes().put(USER_ID_ATTRIBUTE, tokenDetails.userId());
        session.getAttributes().put(TOKEN_ATTRIBUTE, token);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String username = session.getAttributes().get(USERNAME_ATTRIBUTE).toString();

        log.info("Connection {} with user {} closed with status {}", session.getId(), username, status);
    }
}
