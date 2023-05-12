package dev.peytob.rpg.client.module.graphic.context.component;

import dev.peytob.rpg.ecs.component.Component;

public final class MeshComponent implements Component {

    private Integer meshId;

    public MeshComponent(Integer meshId) {
        this.meshId = meshId;
    }

    public Integer getMeshId() {
        return meshId;
    }

    public void setMeshId(Integer meshId) {
        this.meshId = meshId;
    }
}
