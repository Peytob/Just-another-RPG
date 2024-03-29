package dev.peytob.rpg.ecs.model;

public enum SystemDefaultOrder {;

    public static final int BEFORE_ALL = Integer.MIN_VALUE + 1024;

    public static final int UPDATE_POOLING = 1 << 7;

    public static final int INPUT_HANDLING = UPDATE_POOLING << 1;

    public static final int WORLD_STATE_UPDATING = INPUT_HANDLING << 1;

    public static final int RENDERING = WORLD_STATE_UPDATING << 1;

    public static final int AFTER_ALL = Integer.MAX_VALUE - 1024;
}
