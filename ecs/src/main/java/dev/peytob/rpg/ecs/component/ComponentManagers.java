package dev.peytob.rpg.ecs.component;

public enum ComponentManagers {;

    public static ComponentManager mutable() {
        return new SimpleComponentManager();
    }

    public static ComponentManager unmodifiable(ComponentManager componentManager) {
        if (componentManager instanceof UnmodifiableComponentManager unmodifiableComponentManager) {
            return unmodifiableComponentManager;
        }

        return new UnmodifiableComponentManager(componentManager);
    }
}
