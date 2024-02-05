package dev.peytob.rpg.core.gameplay.ecs.component;

import dev.peytob.rpg.ecs.component.Component;
import dev.peytob.rpg.math.vector.Vec2;

public class PositionComponent implements Component {

    private Vec2 position;

    public PositionComponent(Vec2 position) {
        this.position = position;
    }

    public Vec2 getPosition() {
        return position;
    }

    public void setPosition(Vec2 position) {
        this.position = position;
    }
}
