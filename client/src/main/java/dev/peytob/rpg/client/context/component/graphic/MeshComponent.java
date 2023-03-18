package dev.peytob.rpg.client.context.component.graphic;

import dev.peytob.rpg.client.resource.Mesh;
import dev.peytob.rpg.ecs.component.Component;

public final class MeshComponent implements Component {

    private Mesh mesh;

    public MeshComponent(Mesh mesh) {
        this.mesh = mesh;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }
}
