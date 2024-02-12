package dev.peytob.rpg.client.graphic.model.render;

import dev.peytob.rpg.client.graphic.resource.Sprite;
import dev.peytob.rpg.math.vector.Vec2;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class RenderingQueue {

    private final Queue<RenderingSprite> spriteQueue;

    public RenderingQueue() {
        this.spriteQueue = new ConcurrentLinkedQueue<>();
    }

    public boolean add(RenderingSprite renderingSprite) {
        return spriteQueue.add(renderingSprite);
    }

    public boolean add(Sprite sprite, Vec2 position) {
        return add(new RenderingSprite(sprite, position));
    }

    public boolean isEmpty() {
        return spriteQueue.isEmpty();
    }

    public void clear() {
        spriteQueue.clear();
    }
}
