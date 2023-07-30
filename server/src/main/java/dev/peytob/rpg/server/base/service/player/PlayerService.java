package dev.peytob.rpg.server.base.service.player;

import dev.peytob.rpg.server.base.resource.entity.Player;

public interface PlayerService {

    Player getPlayerById(int id);

    Player getPlayerById(String textId);

    boolean createPlayer(Player player);

    boolean removePlayer(Player player);
}
