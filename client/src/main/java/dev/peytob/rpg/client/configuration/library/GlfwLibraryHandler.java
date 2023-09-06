package dev.peytob.rpg.client.configuration.library;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.lwjgl.glfw.GLFW.*;

public final class GlfwLibraryHandler implements StaticLibraryHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlfwLibraryHandler.class);

    @Override
    public boolean initialize() {
        logger.info("Initializing GLFW library");

        GLFWErrorCallback.createPrint(System.err).set();

        boolean isGlfwInitialized = glfwInit();

        if (isGlfwInitialized) {
            logger.info("GLFW library has been initialized!");
        } else {
            logger.info("Error while initializing GLFW library!");
        }

        return isGlfwInitialized;
    }

    @Override
    public void destroy() {
        logger.info("Destroying GLFW library");
        glfwTerminate();
        logger.info("GLFW library has been destroyed");
    }

    @Override
    public String getName() {
        return "GLFW";
    }
}
