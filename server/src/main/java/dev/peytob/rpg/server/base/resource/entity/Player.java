package dev.peytob.rpg.server.base.resource.entity;

import dev.peytob.rpg.engine.resource.Resource;
import dev.peytob.rpg.math.vector.Vec2;

import static dev.peytob.rpg.math.vector.Vectors.immutableVec2;

public class Player extends Entity implements Resource {

    private final Integer id;

    private final String textId;

    private Vec2 position;

    public Player(Integer id, String textId) {
        this.id = id;
        this.textId = textId;
        this.position = immutableVec2();
    }

    @Override
    public Integer id() {
        return this.id;
    }

    @Override
    public String textId() {
        return this.textId;
    }

    public Vec2 getPosition() {
        return position;
    }

    public void setPosition(Vec2 position) {
        this.position = position;
    }
}
