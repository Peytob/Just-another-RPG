package dev.peytob.rpg.server.base.event.player;

import dev.peytob.rpg.core.gameplay.model.world.tilemap.layer.TilemapLayer;
import dev.peytob.rpg.core.gameplay.model.world.tilemap.layer.TilemapLayers;
import dev.peytob.rpg.core.resource.Tile;
import dev.peytob.rpg.server.base.repository.WorldRepository;
import dev.peytob.rpg.server.base.resource.User;
import dev.peytob.rpg.server.base.resource.world.World;
import dev.peytob.rpg.server.base.resource.world.entity.Player;
import dev.peytob.rpg.server.base.service.user.UserService;
import dev.peytob.rpg.server.server.event.auth.instance.UserLoginEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;

import static dev.peytob.rpg.math.vector.Vectors.immutableVec2i;

@Component
public class WorldPlayerOnAuthCreator {

    private static final Logger log = LoggerFactory.getLogger(WorldPlayerOnAuthCreator.class);
    private static final String MOCK_WORLD_ID = "testWorld";

    private final WorldRepository worldRepository;

    private final UserService userService;

    public WorldPlayerOnAuthCreator(WorldRepository worldRepository, UserService userService) {
        this.worldRepository = worldRepository;
        this.userService = userService;
    }

    @EventListener(UserLoginEvent.class)
    public void createUserPlayer(UserLoginEvent userLoginEvent) {
        User user = userService.getUserById(userLoginEvent.userId());

        if (user == null) {
            log.warn("User with id {} not found", userLoginEvent.userId());
            return;
        }

        World world = worldRepository.getById(MOCK_WORLD_ID);
        user.setWorldPlayer(new Player(world, user));
    }

    @PostConstruct
    private void createMockDefaultWorld() {
        if (!worldRepository.contains(MOCK_WORLD_ID)) {
            log.info("Creating beautiful mock world!");
            World world = new World(MOCK_WORLD_ID, generateRandomTilemap());
            worldRepository.append(world);
        }
    }

    private TilemapLayer generateRandomTilemap() {
        TilemapLayer tilemapLayer = TilemapLayers.mutable(immutableVec2i(16, 16));
        Tile[] mockTiles = new Tile[] {
                new Tile("blue_tile"),
                new Tile("red_tile"),
                new Tile("pink_tile"),
                new Tile("green_tile")
        };

        Random random = new Random();

        for (int x = 0; x < tilemapLayer.getSizes().x(); x++) {
            for (int y = 0; y < tilemapLayer.getSizes().y(); y++) {
                int mockTileIndex = random.nextInt(mockTiles.length);
                tilemapLayer.setTile(x, y, mockTiles[mockTileIndex]);
            }
        }

        return tilemapLayer;
    }
}
