package dev.peytob.rpg.client.network.utils;

import dev.peytob.rpg.core.network.model.client.ClientEvent;
import dev.peytob.rpg.rpc.interfaces.base.gameplay.ClientEventsDto;
import org.springframework.core.convert.converter.Converter;

import java.util.Collection;
import java.util.Collections;

public class ClientEventsConverter implements Converter<Collection<ClientEvent>, ClientEventsDto> {

    @Override
    public ClientEventsDto convert(Collection<ClientEvent> source) {
        return ClientEventsDto.newBuilder()
            .addAllEvents(Collections.emptyList())
            .build();
    }
}
