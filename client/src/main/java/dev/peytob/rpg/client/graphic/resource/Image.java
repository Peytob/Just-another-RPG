package dev.peytob.rpg.client.graphic.resource;

import dev.peytob.rpg.engine.resource.Resource;

public class Image implements Resource {

    private final String id;

    public Image(String id) {
        this.id = id;
    }

    @Override
    public String id() {
        return id;
    }
}
