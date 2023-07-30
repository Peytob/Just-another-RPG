package dev.peytob.rpg.server.base.service.player;

import dev.peytob.rpg.server.base.repository.PlayerRepository;
import dev.peytob.rpg.server.base.resource.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final Logger log = LoggerFactory.getLogger(PlayerServiceImpl.class);

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

    @Override
    public boolean createPlayer(Player player) {
        log.info("Creating new player with id {} and text id {}", player.id(), player.textId());

        if (playerRepository.contains(player.textId())) {
            log.warn("Player with text id {} is already exists!", player.textId());
            return false;
        }

        if (playerRepository.contains(player.id())) {
            log.warn("Player with id {} is already exists!", player.textId());
            return false;
        }

        return playerRepository.append(player);
    }

    @Override
    public boolean removePlayer(Player player) {
        boolean isPlayerRemoved = playerRepository.remove(player);

        if (!isPlayerRemoved) {
            log.warn("Removing not exists player {} ({})", player.id(), player.textId());
        }

        return isPlayerRemoved;
    }
}
