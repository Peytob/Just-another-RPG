package dev.peytob.rpg.core.network.model.client;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dev.peytob.rpg.ecs.event.Event;

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, property = "__type")
public record ClientEvent<T>(
    T data
) implements Event {

    public static <T> ClientEvent<T> from(T data) {
        return new ClientEvent<>(data);
    }
}
