package dev.peytob.rpg.client.graphic.service;

import dev.peytob.rpg.client.graphic.repository.SpriteRepository;
import dev.peytob.rpg.client.graphic.resource.Sprite;
import dev.peytob.rpg.client.graphic.resource.Texture;
import dev.peytob.rpg.math.geometry.Rect;
import dev.peytob.rpg.math.geometry.RectI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static dev.peytob.rpg.math.geometry.Rectangles.normalizeInside;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpriteService {

    private final SpriteRepository spriteRepository;

    public Sprite createSprite(String id, Texture texture, Rect normalizedSpriteRect) {
        log.info("Creating new sprite with id {} from texture {}", id, texture);

        Sprite sprite = new Sprite(id, texture, normalizedSpriteRect);
        spriteRepository.append(sprite);
        return sprite;
    }

    public Sprite createSprite(String id, Texture texture, RectI spriteRect) {
        Rect normalizedSpriteRect = normalizeInside(spriteRect, texture.sizes());
        return createSprite(id, texture, normalizedSpriteRect);
    }

    public boolean removeSprite(Sprite sprite) {
        log.info("Removing sprite with id {}", sprite.id());
        return spriteRepository.remove(sprite);
    }

    public Optional<Sprite> getSpriteById(String spriteId) {
        return spriteRepository.getById(spriteId);
    }
}
