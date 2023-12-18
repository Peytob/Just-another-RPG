package dev.peytob.rpg.client.configuration.library;

import lombok.extern.slf4j.Slf4j;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.*;

@Slf4j
public final class GlfwLibraryHandler implements StaticLibraryHandler {

    @Override
    public boolean initialize() {
        log.info("Initializing GLFW library");

        GLFWErrorCallback.createPrint(System.err).set();

        boolean isGlfwInitialized = glfwInit();

        if (isGlfwInitialized) {
            log.info("GLFW library has been initialized!");
        } else {
            log.info("Error while initializing GLFW library!");
        }

        return isGlfwInitialized;
    }

    @Override
    public void destroy() {
        log.info("Destroying GLFW library");
        glfwTerminate();
        log.info("GLFW library has been destroyed");
    }

    @Override
    public String getName() {
        return "GLFW";
    }
}
