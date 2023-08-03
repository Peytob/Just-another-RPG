package dev.peytob.rpg.server.base.resource.world.entity;

import dev.peytob.rpg.math.vector.Vec2;
import dev.peytob.rpg.server.base.resource.world.World;

import static dev.peytob.rpg.math.vector.Vectors.immutableVec2;

/**
 * Base class for all entities, that can be located and interacted in the world.
 */
public abstract class Entity {

    private final World world;

    private final String textId;

    private int health;

    private int maxHealth;

    private Vec2 position;

    public Entity(World world, String textId) {
        this.world = world;
        this.textId = textId;

        this.health = 0;
        this.maxHealth = 0;
        this.position = immutableVec2();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public Vec2 getPosition() {
        return position;
    }

    public void setPosition(Vec2 position) {
        this.position = position;
    }

    public World getWorld() {
        return world;
    }

    public String getTextId() {
        return textId;
    }
}
