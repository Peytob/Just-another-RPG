package dev.peytob.rpg.ecs.model;

public enum SystemDefaultOrder {;

    public static final Integer BEFORE_ALL = Integer.MIN_VALUE + 1024;

    public static final Integer UPDATE_POOLING = 1 << 7;

    public static final Integer INPUT_HANDLING = UPDATE_POOLING << 1;

    public static final Integer WORLD_STATE_UPDATING = INPUT_HANDLING << 1;

    public static final Integer RENDERING = WORLD_STATE_UPDATING << 1;

    public static final Integer AFTER_ALL = Integer.MAX_VALUE - 1024;
}
