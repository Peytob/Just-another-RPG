package dev.peytob.rpg.client.input.hid.model;

import static org.lwjgl.glfw.GLFW.*;

public final class KeyModifiers {

    private final int mods;

    public KeyModifiers(int mods) {
        this.mods = mods;
    }

    public boolean isShiftPresent() {
        return (mods & GLFW_MOD_SHIFT) != 0;
    }

    public boolean isControlPresent() {
        return (mods & GLFW_MOD_CONTROL) != 0;
    }

    public boolean isAltPresent() {
        return (mods & GLFW_MOD_ALT) != 0;
    }

    public boolean isSuperPresent() {
        return (mods & GLFW_MOD_SUPER) != 0;
    }

    public boolean isCapsPresent() {
        return (mods & GLFW_MOD_CAPS_LOCK) != 0;
    }

    public boolean isNumLockPresent() {
        return (mods & GLFW_MOD_NUM_LOCK) != 0;
    }
}
