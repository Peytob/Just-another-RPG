package dev.peytob.rpg.client.network.utils;

import dev.peytob.rpg.core.network.model.server.WorldState;
import dev.peytob.rpg.rpc.interfaces.base.gameplay.AvailableWorldStateDto;
import org.springframework.core.convert.converter.Converter;

public class AvailableWorldStateConverter implements Converter<AvailableWorldStateDto, WorldState> {

    @Override
    public WorldState convert(AvailableWorldStateDto source) {
        return new WorldState();
    }
}
