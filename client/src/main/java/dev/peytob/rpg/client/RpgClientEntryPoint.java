package dev.peytob.rpg.client;

import dev.peytob.rpg.client.configuration.library.GlfwLibraryHandler;
import dev.peytob.rpg.client.configuration.library.StaticLibraryHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
public class RpgClientEntryPoint {

    // C libraries handlers, that allows application to initialize and free library resources.
    // Also, this guaranties, that libraries would be called from main application thread.
    private static final StaticLibraryHandler[] STATIC_LIBRARY_HANDLERS = {
        new GlfwLibraryHandler()
    };

    public static void main(String[] args) {
        initializeStaticCLibraries();

        ConfigurableApplicationContext context = SpringApplication
            .run(RpgClientEntryPoint.class);

        ClientEngineStarter clientEngineStarter = context.getBean(ClientEngineStarter.class);
        clientEngineStarter.initializeAndStartGameCycle();

        // Should be closed before destroying C libraries, because
        // some system resources may be not free after destroying libraries.
        context.close();
        destroyStaticCLibraries();
    }

    private static void initializeStaticCLibraries() {
        for (StaticLibraryHandler library : STATIC_LIBRARY_HANDLERS) {
            boolean isLibraryInitialized = library.initialize();

            if (!isLibraryInitialized) {
                throw new IllegalStateException("Library " + library.getName() + " has not been initialized!");
            }
        }
    }

    private static void destroyStaticCLibraries() {
        for (StaticLibraryHandler library : STATIC_LIBRARY_HANDLERS) {
            log.info("Destroying library {} has been destroyed", library.getName());
            library.destroy();
            log.info("Library {} has been destroyed", library.getName());
        }
    }
}
