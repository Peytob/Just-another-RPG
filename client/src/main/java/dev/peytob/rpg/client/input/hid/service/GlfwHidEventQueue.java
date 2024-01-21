package dev.peytob.rpg.client.input.hid.service;

import dev.peytob.rpg.client.graphic.model.glfw.Window;
import dev.peytob.rpg.client.input.hid.ecs.event.*;
import dev.peytob.rpg.client.input.hid.model.KeyAction;
import dev.peytob.rpg.client.input.hid.model.KeyModifiers;
import dev.peytob.rpg.client.input.hid.model.KeyboardKey;
import dev.peytob.rpg.client.input.hid.model.MouseButton;
import org.lwjgl.glfw.GLFW;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class GlfwHidEventQueue implements HidEventQueue {

    private final Queue<HidEvent> hidEventQueue;

    public GlfwHidEventQueue() {
        this.hidEventQueue = new ConcurrentLinkedQueue<>();
    }

    @Override
    public boolean appendEvent(HidEvent hidEvent) {
        return hidEventQueue.add(hidEvent);
    }

    @Override
    public HidEvent pollEvent() {
        return hidEventQueue.poll();
    }

    @Override
    public boolean isEventQueueEmpty() {
        return hidEventQueue.isEmpty();
    }

    @Override
    public int getEventQueueSize() {
        return hidEventQueue.size();
    }

    public void subscribe(Window window) {
        GLFW.glfwSetCursorPosCallback(window.getPointer(), (windowPointer, x, y) -> {
            CursorPositionEvent event = new CursorPositionEvent(x, y, x - window.getWidth() / 2.0, window.getHeight() / 2.0);
            appendEvent(event);
        });

        GLFW.glfwSetKeyCallback(window.getPointer(), (windowPointer, key, scancode, action, mods) -> {
            KeyboardKeyEvent event = new KeyboardKeyEvent(new KeyboardKey(scancode, key), KeyAction.fromGlfwAction(action), new KeyModifiers(mods));
            appendEvent(event);
        });

        GLFW.glfwSetMouseButtonCallback(window.getPointer(), (windowPointer, button, action, mods) -> {
            MouseButtonEvent event = new MouseButtonEvent(new MouseButton(button), KeyAction.fromGlfwAction(action), new KeyModifiers(mods));
            appendEvent(event);
        });

        GLFW.glfwSetScrollCallback(window.getPointer(), (windowPointer, xOffset, yOffset) -> {
            MouseScrollEvent mouseScrollEvent = new MouseScrollEvent(xOffset, yOffset);
            appendEvent(mouseScrollEvent);
        });
    }
}
