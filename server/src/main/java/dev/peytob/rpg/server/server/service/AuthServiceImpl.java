package dev.peytob.rpg.server.server.service;

import dev.peytob.rpg.core.module.base.model.world.tilemap.Tilemap;
import dev.peytob.rpg.core.module.base.model.world.tilemap.Tilemaps;
import dev.peytob.rpg.core.module.base.resource.Tile;
import dev.peytob.rpg.server.base.repository.WorldRepository;
import dev.peytob.rpg.server.base.resource.User;
import dev.peytob.rpg.server.base.resource.world.World;
import dev.peytob.rpg.server.base.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

import static dev.peytob.rpg.math.vector.Vectors.immutableVec2i;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserService userService;

    private final WorldRepository worldRepository;

    public AuthServiceImpl(UserService userService, WorldRepository worldRepository) {
        this.userService = userService;
        this.worldRepository = worldRepository;
    }

    @Override
    public void login(String token) {
        // TODO Temporary just creates new player and test world
        log.info("User is trying to login in server");

        if (!worldRepository.contains("testWorld")) {
            log.info("Creating beautiful mock world!");
            World world = new World(1, "testWorld", generateRandomTilemap());
            worldRepository.append(world);
        }

        // Getting user id by token...
        // Getting user data by id...
        // Making token -> user cache
        userService.createUser(new User(token.hashCode(), "test", token));
        // Sending player login events...
    }

    @Override
    public void logout(String token) {
        log.info("User is logout from server");

        // Getting player by token...
        // Invalidating token -> user cache...
        userService.removeUser(new User(token.hashCode(), "test", token));
        // Sending player logout events...
    }

    private Tilemap generateRandomTilemap() {
        Tilemap tilemap = Tilemaps.mutable(immutableVec2i(16, 16));
        Tile[] mockTiles = new Tile[] {
                new Tile(1, "blue_tile"),
                new Tile(2, "red_tile"),
                new Tile(3, "pink_tile"),
                new Tile(4, "green_tile")
        };

        Random random = new Random();

        for (int x = 0; x < tilemap.getSizes().x(); x++) {
            for (int y = 0; y < tilemap.getSizes().y(); y++) {
                int mockTileIndex = random.nextInt(mockTiles.length);
                tilemap.setTile(x, y, mockTiles[mockTileIndex]);
            }
        }

        return tilemap;
    }
}
