package dev.peytob.rpg.server.server.event.auth.instance;

import dev.peytob.rpg.server.server.event.ServerEvent;

public record UserLoginEvent(
    int userId
) implements ServerEvent {
}
