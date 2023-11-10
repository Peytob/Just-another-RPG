package dev.peytob.rpg.auth.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AuthGatewayEntryPoint {

    public static void main(String[] args) {
        SpringApplication.run(AuthGatewayEntryPoint.class);
    }
}
