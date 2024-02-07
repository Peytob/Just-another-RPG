package dev.peytob.rpg.client.graphic.model;

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

    public boolean offer(RenderingSprite renderingSprite) {
        return spriteQueue.offer(renderingSprite);
    }

    public RenderingSprite remove() {
        return spriteQueue.remove();
    }

    public RenderingSprite poll() {
        return spriteQueue.poll();
    }

    public RenderingSprite element() {
        return spriteQueue.element();
    }

    public RenderingSprite peek() {
        return spriteQueue.peek();
    }

    public int size() {
        return spriteQueue.size();
    }

    public boolean isEmpty() {
        return spriteQueue.isEmpty();
    }

    public boolean remove(RenderingSprite renderingSprite) {
        return spriteQueue.remove(renderingSprite);
    }
}
