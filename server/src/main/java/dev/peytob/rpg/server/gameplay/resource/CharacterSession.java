package dev.peytob.rpg.server.gameplay.resource;


import dev.peytob.rpg.engine.resource.Resource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class CharacterSession implements Resource {

    @Accessors(fluent = true)
    private final String id;

    private Instant lastPullInstant;

    private Character character;
}
