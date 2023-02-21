package dev.peytob.rpg.client.model.graphics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL33.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window implements AutoCloseable {

    private static final Logger logger = LoggerFactory.getLogger(Window.class);

    private final long pointer;

    public static Window create(String title, int width, int height) {
        logger.info("Creating new GLFW window");

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

        logger.info("Window ({}) has been created", pointer);

        return new Window(pointer);
    }

    public static void destroy(long pointer) {
        logger.info("Destroying window ({})", pointer);
        glfwFreeCallbacks(pointer);
        glfwDestroyWindow(pointer);
        logger.info("Window ({}) has been destroyed", pointer);
    }

    private Window(long pointer) {
        this.pointer = pointer;
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
}
