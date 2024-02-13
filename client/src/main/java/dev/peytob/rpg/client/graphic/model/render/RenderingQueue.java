package dev.peytob.rpg.client.graphic.model.render;

import dev.peytob.rpg.client.graphic.resource.Sprite;
import dev.peytob.rpg.math.vector.Vec2;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Queue;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

@Component
public class RenderingQueue implements Iterable<RenderingSprite> {

    private final Queue<RenderingSprite> spriteQueue;

    public RenderingQueue() {
        this.spriteQueue = new ConcurrentLinkedQueue<>();
    }

    public boolean add(RenderingSprite renderingSprite) {
        return spriteQueue.add(renderingSprite);
    }

    public boolean add(Sprite sprite, Vec2 position, Vec2 sizes) {
        return add(new RenderingSprite(sprite, position, sizes));
    }

    public boolean isEmpty() {
        return spriteQueue.isEmpty();
    }

    public void clear() {
        spriteQueue.clear();
    }

    public int size() {
        return spriteQueue.size();
    }

    @Override
    public void forEach(Consumer<? super RenderingSprite> action) {
        spriteQueue.forEach(action);
    }

    @Override
    public Iterator<RenderingSprite> iterator() {
        return spriteQueue.iterator();
    }

    @Override
    public Spliterator<RenderingSprite> spliterator() {
        return spriteQueue.spliterator();
    }
}
