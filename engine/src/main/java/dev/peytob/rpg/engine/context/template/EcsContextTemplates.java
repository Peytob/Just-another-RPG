package dev.peytob.rpg.engine.context.template;

public enum EcsContextTemplates {;

    private final static EcsContextTemplate EMPTY = new EmptyEcsContextTemplate();

    public static EcsContextTemplate empty() {
        return EMPTY;
    }
}
