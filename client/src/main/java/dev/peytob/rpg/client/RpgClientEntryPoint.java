package dev.peytob.rpg.client;

import dev.peytob.rpg.engine.RpgEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RpgClientEntryPoint {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication
                .run(RpgClientEntryPoint.class);

        RpgEngine engine = context.getBean(RpgEngine.class);
        engine.initialize();
    }
}