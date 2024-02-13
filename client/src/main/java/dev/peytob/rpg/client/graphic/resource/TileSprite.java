package dev.peytob.rpg.client.graphic.resource;

import dev.peytob.rpg.core.gameplay.resource.Tile;
import dev.peytob.rpg.engine.resource.Resource;

public record TileSprite(String id, Tile tile, Sprite sprite) implements Resource {
}
