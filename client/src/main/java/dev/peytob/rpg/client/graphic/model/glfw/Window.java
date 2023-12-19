package dev.peytob.rpg.client.graphic.model.glfw;

import lombok.extern.slf4j.Slf4j;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL33.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

@Slf4j
public class Window implements AutoCloseable {

    private final long pointer;

    private int width;

    private int height;

    public static Window create(String title, int width, int height) {
        log.info("Creating new GLFW window");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        long pointer = glfwCreateWindow(width, height, title, NULL, NULL);

        glfwMakeContextCurrent(pointer);
        glfwSwapInterval(1);
        glfwSetInputMode(pointer, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        createCapabilities();

        // TODO Getting window framebuffer sizes
        glViewport(0, 0, width, height);

        log.info("Window ({}) has been created", pointer);

        return new Window(pointer, width, height);
    }

    public static void destroy(long pointer) {
        log.info("Destroying window ({})", pointer);
        glfwFreeCallbacks(pointer);
        glfwDestroyWindow(pointer);
        log.info("Window ({}) has been destroyed", pointer);
    }

    private Window(long pointer, int width, int height) {
        this.pointer = pointer;
        this.width = width;
        this.height = height;
    }

    public boolean isShouldClose() {
        return glfwWindowShouldClose(pointer);
    }

    public void setCloseFlag(boolean flag) {
        glfwSetWindowShouldClose(pointer, flag);
    }

    public void pollEvents() {
        glfwPollEvents();
    }

    public void display() {
        glfwSwapBuffers(pointer);
    }

    public void show() {
        glfwShowWindow(pointer);
    }

    @Override
    public void close() {
        destroy(pointer);
    }

    public long getPointer() {
        return pointer;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}