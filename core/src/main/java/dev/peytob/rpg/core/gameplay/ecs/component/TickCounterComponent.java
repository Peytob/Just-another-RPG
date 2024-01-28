package dev.peytob.rpg.core.gameplay.ecs.component;

import dev.peytob.rpg.ecs.component.Component;

public class TickCounterComponent implements Component {

    private long currentTick;

    private boolean isAutoincrementEnabled;

    public TickCounterComponent() {
        this(true);
    }

    public TickCounterComponent(boolean isAutoincrementEnabled) {
        this.isAutoincrementEnabled = isAutoincrementEnabled;
        this.currentTick = 0;
    }

    public long getCurrentTick() {
        return currentTick;
    }

    public void incrementCurrentTick() {
        this.currentTick += 1;
    }

    public boolean isAutoincrementEnabled() {
        return isAutoincrementEnabled;
    }

    public void setAutoincrementEnabled(boolean autoincrementEnabled) {
        isAutoincrementEnabled = autoincrementEnabled;
    }
}
