package dev.peytob.rpg.client.gameplay.ecs.component;

import dev.peytob.rpg.core.gameplay.resource.World;
import dev.peytob.rpg.ecs.component.SingletonComponent;

public class WorldComponent implements SingletonComponent {

    private World world;

    public WorldComponent(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
