package dev.peytob.rpg.server.base.service.player;

import dev.peytob.rpg.server.base.repository.PlayerRepository;
import dev.peytob.rpg.server.base.resource.entity.Player;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player getPlayerById(int id) {
        return playerRepository.getById(id);
    }

    @Override
    public Player getPlayerById(String textId) {
        return playerRepository.getById(textId);
    }
}
